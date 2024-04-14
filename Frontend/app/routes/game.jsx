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
        <div className="flex justify-center mt-[70px]">
          <div className="flex flex-col">
            <div className="relative border-t-[3px] border-l-[3px] border-r-[3px] border-white border-solid h-[43px] mb-[15px]">
              <span className="absolute top-[8px] left-[25px] text-[30px] noto">
                Question
              </span>
            </div>
            <div className="w-[550px] h-[360px] bg-gradient-to-b from-secondary to-accent rounded-[10px]"></div>
          </div>
          <img className="ml-[30px]" src="/image/Men.png"></img>
        </div>
      </section>
    </div>
  );
};
export default Game;
