import gameStyle from "~/styles/game.css";
import CRT_STYLE from "~/styles/crt.css";

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
            <div className="question-printer"></div>
            <div className="answer-printer">
              <h4 className="noto">Select Answer</h4>
            </div>
          </div>
          <img className="ml-[30px] h-[468px]" src="/image/Men.png"></img>
        </div>
      </section>
    </div>
  );
};
export default Game;
