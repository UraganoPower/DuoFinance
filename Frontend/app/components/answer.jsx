import PointerGame from "../svg/pointerGame";

const Answer = ({ text, onClick, letterIndex }) => {
  return (
    <div className="answer" onClick={onClick}>
      <h4 className="noto">
        {letterIndex}. {text}
      </h4>
      <PointerGame clasName={"pointer-game  mt-[5px]"}></PointerGame>
    </div>
  );
};

export default Answer;
