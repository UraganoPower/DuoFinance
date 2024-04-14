import Typewriter from "typewriter-effect";

const Question = ({ children, className }) => {
  return (
    <Typewriter
      options={{
        strings: [`<span class="${className}">${children}</span>`],
        autoStart: true,
        cursor: "",
        deleteSpeed: Infinity,
        delay: 15,
      }}
    ></Typewriter>
  );
};
export default Question;
