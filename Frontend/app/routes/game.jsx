import gameStyle from "~/styles/game.css";
import CRT_STYLE from "~/styles/crt.css";
import Answer from "../components/answer";
import Question from "../components/question";
import { useEffect, useRef, useState } from "react";
import PlayButton from "../components/PlayButton";
import { Button } from "@nextui-org/button";
import { useNavigate } from "@remix-run/react";
import { StatusCodes } from "http-status-codes";
import { Input as NextuiInput } from "@nextui-org/input";
import { Tooltip } from "@nextui-org/tooltip";
import XMark from "../svg/x-mark";

export const links = () => [
  { rel: "stylesheet", href: gameStyle },
  { rel: "stylesheet", href: CRT_STYLE },
];

let questionsForBack = [];
const IMG_LINK = [
  { src: "image/Men.png", alt: "Men", class: "img-men" },
  { src: "image/Question1.png", class: "img-question-1" },
  { src: "image/Question2.png", class: "img-question-2" },
  { src: "image/Question3.png", class: "img-question-3" },
];
const Game = () => {
  const sheetRef = useRef(null);
  const [isGameRunning, setIsGameRunning] = useState(false);
  const [currentQuestion, setCurrentQuestion] = useState({
    questionText: "Are you ready for the super Duo Finance?",
  });
  const [questions, setQuestions] = useState([]);
  const [questionCounter, setQuestionCounter] = useState(0);
  const navigate = useNavigate();
  const [user, setUser] = useState({});
  const renameRef = useRef();
  const [renameError, setRenameError] = useState(null);
  const modal = useRef(null);
  const [gamesPlay, setGamePlay] = useState([]);
  const [average, setAverage] = useState(0);
  const [megaloAudio, setMegaloAudio] = useState(null);
  const [bipAudio, setBipAudio] = useState(null);
  const [selectaudio, setSelectAudio] = useState(null);

  useEffect(() => {
    setMegaloAudio(new Audio("/music/megalovania.mp3"));
    setBipAudio(new Audio("/music/bip.mp3"));
    setSelectAudio(new Audio("/music/select.mp3"));

    fetch("http://localhost:8080/api/user", {
      method: "Get",
      credentials: "include",
    })
      .then((res) => {
        if (res.status !== StatusCodes.OK) {
          navigate("/login");
        }
        return res.json();
      })
      .then((data) => {
        if (data.roleId == 1) {
          navigate("/login");
        } else {
          setUser(data);
        }
      });

    fetchGames();
    fetchAverage();
  }, []);

  const playBip = () => {
    bipAudio.volume = 0.1;
    bipAudio.play();
  };

  const stopBip = () => {
    bipAudio.pause();
    bipAudio.currentTime = 0;
  };

  const playMegalo = () => {
    megaloAudio.volume = 0.1;
    megaloAudio.play();
  };

  const playSelect = () => {
    selectaudio.volume = 0.5;
    selectaudio.play();
  };

  const fetchQuestions = async () => {
    try {
      const res = await fetch("http://localhost:8080/api/random-questions", {
        method: "GET",
        credentials: "include",
      });
      const data = await res.json();
      return data;
    } catch (e) {
      throw new Error("Cant fetch questions for some reason");
    }
  };

  const nextQuestion = async (answer) => {
    const questionToSend = {
      questionId: currentQuestion.questionId,
      answer: answer,
    };

    questionsForBack = [...questionsForBack, questionToSend];
    setCurrentQuestion(questions.pop());
    setQuestionCounter(questionCounter + 1);

    if (questions.length == 0) {
      //send to backend
      await sendValidationToBackEnd();
      setIsGameRunning(false);
      return;
    }
  };

  const sendValidationToBackEnd = async () => {
    //fetch
    try {
      setQuestionCounter(0);
      const res = await fetch("http://localhost:8080/api/submit", {
        method: "POST",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(questionsForBack),
      });
      const howManyGood = await res.json();

      //reset ansewer
      questionsForBack = [];
      setCurrentQuestion({
        questionText: `Nice ${howManyGood} out of 3. Are you ready for another super Duo Finance?`,
      });
    } catch (e) {
      throw new Error(
        "Cant send questions for some reason inside sendValidationToBackEnd"
      );
    }
    fetchGames();
    fetchAverage();
  };

  const handleStartGame = async () => {
    playMegalo();
    //load questions
    setQuestionCounter(questionCounter + 1);
    const data = await fetchQuestions();
    setCurrentQuestion(data[0]);
    setQuestions(data);

    //start game
    setIsGameRunning(true);
  };

  const toggleSheet = () => {
    sheetRef.current.classList.toggle("collapsed");
  };

  const openModal = () => {
    modal.current.showModal();
  };

  const closeModal = () => {
    modal.current.close();
  };

  const logout = () => {
    fetch("http://localhost:8080/api/logout", {
      method: "POST",
      credentials: "include",
    }).then((res) => {
      if (res.status === StatusCodes.OK) {
        navigate("/login");
      }
    });
  };

  const deleteAccount = () => {
    fetch("http://localhost:8080/api/user", {
      method: "DELETE",
      credentials: "include",
    }).then((res) => {
      if (res.status === StatusCodes.OK) {
        navigate("/login");
      }
    });
  };

  const fetchGames = () => {
    fetch("http://localhost:8080/api/game", {
      method: "GET",
      credentials: "include",
    })
      .then((res) => {
        if (res.status !== StatusCodes.OK) {
          throw new Error("Cant fetch games for some reason");
        }
        return res.json();
      })
      .then((data) => {
        setGamePlay(data);
      });
  };

  const fetchAverage = () => {
    fetch("http://localhost:8080/api/game/average", {
      method: "GET",
      credentials: "include",
    })
      .then((res) => {
        if (res.status !== StatusCodes.OK) {
          throw new Error("Cant fetch average for some reason");
        }
        return res.json();
      })
      .then((data) => {
        setAverage(data);
      });
  };

  const rename = () => {
    fetch("http://localhost:8080/api/user", {
      method: "PUT",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        username: renameRef.current.value,
      }),
    }).then((res) => {
      if (res.status === StatusCodes.OK) {
        setUser({
          ...user,
          username: renameRef.current.value,
        });
        setRenameError(null);
      } else {
        setRenameError("Cant rename user for some reason");
      }
    });
  };

  return (
    <div className="screen scanlines">
      <dialog ref={modal} className="modal">
        <div className="flex flex-col">
          <p className="noto modal-text text-white">
            Are you sure you want to delete your account?
          </p>
          <p className="noto modal-text text-red-500">
            This action cannot be undone
          </p>
          <div className="flex mt-[10px]">
            <Button
              onClick={closeModal}
              className=" bg-primary text-white noto text-[18px] rounded-sm"
            >
              close
            </Button>
            <Button
              onClick={deleteAccount}
              className="ml-[20px] bg-red-500 text-white noto text-[18px] rounded-sm"
            >
              Delete
            </Button>
          </div>
        </div>
      </dialog>
      <section className="game-section">
        <nav className="game-navigation">
          <h1 className="noto game-nav-title ">Duo Finance</h1>
          <div className="flex items-center">
            <span className="user-name noto">{user.username}</span>
            <button onClick={toggleSheet} className="user-button">
              <span className="initial noto">
                {user?.username?.charAt(0).toUpperCase()}
              </span>
            </button>
          </div>
        </nav>
        <div className="main-game-wrapper">
          <div className="flex flex-col">
            <div className="question-border-printer">
              <span className="question-counter noto">
                {!isGameRunning ? (
                  "Question"
                ) : (
                  <>
                    {`Question ${questionCounter}`}
                    <span className="noto text-[20px]">/3</span>
                  </>
                )}
              </span>
            </div>
            <div className="question-printer">
              <Question className="noto text-[30px] leading-[50px] select-none scrollable overflow-y-auto h-[280px] w-[490px] block">
                {currentQuestion ? currentQuestion.questionText : ""}
              </Question>
            </div>
            <div className="answer-printer">
              {!isGameRunning ? (
                <PlayButton onClick={handleStartGame} />
              ) : (
                <>
                  <h4 className="noto text-[20px] mb-[20px]">Select Answer</h4>
                  <div className="answers">
                    <Answer
                      text={currentQuestion.choiceA}
                      letterIndex="A"
                      onClick={() => {
                        nextQuestion(currentQuestion.choiceA);
                        playSelect();
                      }}
                      onHover={() => {
                        playBip();
                        console.log("bip");
                      }}
                      onMouseLeave={() => {
                        stopBip();
                      }}
                    />
                    <Answer
                      text={currentQuestion.choiceB}
                      letterIndex="B"
                      onClick={() => {
                        nextQuestion(currentQuestion.choiceB);
                        playSelect();
                      }}
                      onHover={() => {
                        playBip();
                      }}
                      onMouseLeave={() => {
                        stopBip();
                      }}
                    />
                    <Answer
                      text={currentQuestion.choiceC}
                      letterIndex="C"
                      onClick={() => {
                        nextQuestion(currentQuestion.choiceC);
                        playSelect();
                      }}
                      onHover={() => {
                        playBip();
                      }}
                      onMouseLeave={() => {
                        stopBip();
                      }}
                    />
                  </div>
                </>
              )}
            </div>
          </div>
          <div className="border-show-men">
            <div className="bg-show-man">
              <img
                className={`absolute ${IMG_LINK[questionCounter].class}`}
                src={`${IMG_LINK[questionCounter].src}`}
              ></img>
            </div>
          </div>
        </div>
      </section>
      <div ref={sheetRef} className="sheet collapsed">
        <div className="wrapper-sheet">
          <div className="flex justify-between items-center">
            <h1 className="noto text-white text-[35px]">
              {" "}
              Duo Finance Setings{" "}
            </h1>
            <XMark className={"cursor-pointer"} onClick={toggleSheet}></XMark>
          </div>
          <Tooltip
            showArrow={true}
            className="p-[9px] noto text-[12px] bg-secondary text-white"
            content="From your account !"
            placement="right"
            offset={7}
          >
            <button
              onClick={logout}
              className="noto w-fit text-white hover:text-secondary transition-[.2s]"
            >
              logout
            </button>
          </Tooltip>

          <NextuiInput
            ref={renameRef}
            className="mt-[40px] w-[400px]"
            radius="none"
            placeholder="Username if you want change"
          ></NextuiInput>
          <div className="flex mt-[20px] ">
            <Button
              onClick={rename}
              className=" bg-primary text-white noto text-[18px] rounded-sm"
            >
              Save
            </Button>
            <Tooltip
              showArrow={true}
              className="p-[8px] noto text-[12px] bg-red-500 text-white"
              content="Delete your account !"
              placement="top"
              offset={-7}
            >
              <Button
                onClick={openModal}
                className="ml-[20px] bg-red-500 text-white noto text-[18px] rounded-sm"
              >
                Delete
              </Button>
            </Tooltip>
          </div>
          {renameError ? (
            <p className="europa text-red-400">{renameError}</p>
          ) : null}

          <div className="flex flex-col h-full overflow-hidden">
            <div className="h-[1px] w-full bg-white mt-[40px]"></div>
            <h1 className="noto text-white text-[35px] mb-[20px]">
              Your last games
            </h1>
            <div className="flex mb-[20px] ">
              <span className="mr-[10px] text-[20px]  noto">Game id</span>
              <span className="text-[20px] ml-[50px]  noto">Score</span>
              <span className="text-[20px] ml-[50px]  noto">
                Average : {average} / 3
              </span>
            </div>
            <div className="h-full w-full  overflow-y-auto  removeScrollBar">
              {gamesPlay.map((game, i) => (
                <div key={`game-${i}`} className="mb-[20px] flex items-center">
                  <div className="ml-[15px] select-none h-[40px] w-[40px] noto text-[20px] rounded-full flex justify-center items-center bg-primary">
                    {game.gameId}
                  </div>
                  <div className="ml-[85px] noto text-[20px]">
                    {game.score} / 3
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default Game;
