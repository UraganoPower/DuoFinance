import PointerGame from "../svg/pointerGame";
import PointerGameR from "../svg/pointerGameR";

const PlayButton = ({ onClick }) => {
  return (
    <button onClick={onClick} className="flex items-center button-play">
      <div className="hover-turn">
        <PointerGameR className="pointer-play h-[50px] w-[50px]" />
      </div>
      <span className="noto text-[70px]">Play</span>
      <div className="hover-turn">
        <PointerGame className="pointer-play-right ml-[10px] h-[50px] w-[50px]" />
      </div>
    </button>
  );
};
export default PlayButton;
