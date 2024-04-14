import gameStyle from "~/styles/game.css";
import CRT_STYLE from "~/styles/crt.css";
import Answer from "../components/answer";
import Question from "../components/question";

export const links = () => [
  { rel: "stylesheet", href: gameStyle },
  { rel: "stylesheet", href: CRT_STYLE },
];

const Game = () => {
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
              <span className="question-counter noto">Question</span>
            </div>
            <div className="question-printer">
              <Question className="noto text-[30px] leading-[50px] select-none scrollable overflow-y-auto h-[280px] w-[490px] block">
                What is a one-year period that companies and governments use for
                financial reporting and budgeting?
              </Question>
            </div>
            <div className="answer-printer">
              <h4 className="noto text-[20px] mb-[20px]">Select Answer</h4>
              <div className="answers">
                <Answer
                  text="Calendar Year"
                  letterIndex="A"
                  onClick={() => {}}
                />
                <Answer text="Fiscal Year" letterIndex="B" onClick={() => {}} />
                <Answer text="Leap Year" letterIndex="C" onClick={() => {}} />
              </div>
            </div>
          </div>
          <img className="ml-[30px] h-[468px]" src="/image/Men.png"></img>
        </div>
      </section>
    </div>
  );
};
export default Game;
