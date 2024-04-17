import PointerGame from "../svg/pointerGame";

const Answer = ({ text, onClick, letterIndex, onHover, onMouseLeave }) => {
  return (
    <div
      className="answer"
      onClick={onClick}
      onMouseOver={onHover}
      onMouseLeave={onMouseLeave}
    >
      <h4 className="noto">
        {letterIndex}. {text}
      </h4>
      <PointerGame className={"pointer-game mt-[5px]"}></PointerGame>
    </div>
  );
};

export default Answer;
