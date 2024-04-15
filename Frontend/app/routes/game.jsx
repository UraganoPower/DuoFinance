import gameStyle from "~/styles/game.css";
import CRT_STYLE from "~/styles/crt.css";
import Answer from "../components/answer";
import Question from "../components/question";
import { useEffect, useState } from "react";
import PlayButton from "../components/PlayButton";

export const links = () => [
  { rel: "stylesheet", href: gameStyle },
  { rel: "stylesheet", href: CRT_STYLE },
];

let questionsForBack = [];
const Game = () => {
  const [isGameRunning, setIsGameRunning] = useState(false);
  const [currentQuestion, setCurrentQuestion] = useState({
    questionText: "Are you ready for the super Duo Finance?",
  });
  const [questions, setQuestions] = useState([]);

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

  useEffect(() => {
    // console.log("questions : ", questions);
    // console.log("currentQuestion : ", currentQuestion);
  }, [questions, currentQuestion]);

  const nextQuestion = async (answer) => {
    const questionToSend = {
      questionId: currentQuestion.questionId,
      answer: answer,
    };

    questionsForBack = [...questionsForBack, questionToSend];
    setCurrentQuestion(questions.pop());

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
  };

  const handleStartGame = async () => {
    //load questions
    const data = await fetchQuestions();
    setCurrentQuestion(data[0]);
    setQuestions(data);

    //start game
    setIsGameRunning(true);
  };

  return (
    <div className="screen scanlines">
      <section className="game-section">
        <nav className="game-navigation">
          <h1 className="noto game-nav-title ">Duo Finance</h1>
          <div className="flex items-center">
            <span className="user-name noto">Bill_cipher</span>
            <button className="user-button">
              <span className="initial noto">B</span>
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
                    {`Question ${questionsForBack.length + 1}`}
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
                      }}
                    />
                    <Answer
                      text={currentQuestion.choiceB}
                      letterIndex="B"
                      onClick={() => {
                        nextQuestion(currentQuestion.choiceB);
                      }}
                    />
                    <Answer
                      text={currentQuestion.choiceC}
                      letterIndex="C"
                      onClick={() => {
                        nextQuestion(currentQuestion.choiceC);
                      }}
                    />
                  </div>
                </>
              )}
            </div>
          </div>
          <img className="ml-[30px] h-[468px]" src="/image/Men.png"></img>
        </div>
      </section>
    </div>
  );
};
export default Game;
