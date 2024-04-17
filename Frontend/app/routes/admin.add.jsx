import { Button } from "@nextui-org/button";
import { Link } from "@remix-run/react";
import { StatusCodes } from "http-status-codes";
import { useRef, useState } from "react";

const AddQuestion = () => {
  const questionRef = useRef();
  const answerRef = useRef();
  const choiceARef = useRef();
  const choiceBRef = useRef();
  const choiceCRef = useRef();
  const [fetchReturn, setFetchReturn] = useState({});
  const [questionAdd, setQuestionAdd] = useState(false);

  const clearInput = () => {
    questionRef.current.value = "";
    answerRef.current.value = "";
    choiceARef.current.value = "";
    choiceBRef.current.value = "";
    choiceCRef.current.value = "";

    setQuestionAdd(false);
  };

  const addQuestion = () => {
    setQuestionAdd(false);
    fetch("http://localhost:8080/api/question", {
      method: "POST",
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        questionText: questionRef.current.value,
        answer: answerRef.current.value,
        choiceA: choiceARef.current.value,
        choiceB: choiceBRef.current.value,
        choiceC: choiceCRef.current.value,
        questionId: null,
      }),
    }).then((res) => {
      console.log(res.status);
      if (res.status === StatusCodes.CREATED) {
        setQuestionAdd(true);
        setFetchReturn({});
      } else {
        res.json().then((data) => setFetchReturn(data));
      }
    });
  };

  return (
    <div className="flex justify-center items-center w-full">
      <div className="flex flex-col mt-[20px] w-[500px]">
        <div className="relative flex flex-col">
          <span className="europa text-[20px]">Question</span>
          <textarea ref={questionRef} className="text-area-admin"></textarea>
          {fetchReturn?.errors?.questionText ? (
            <p className="absolute bottom-[-10px] right-0  text-[10px] text-red-500">
              {fetchReturn.errors.questionText}
            </p>
          ) : null}
        </div>

        <div className="relative flex flex-col">
          <span className="europa text-[20px]">Choice A</span>
          <input className="input-admin" ref={choiceARef} type="text"></input>
          {fetchReturn?.errors?.choiceA ? (
            <p className="absolute bottom-[-10px] right-0  text-[10px] text-red-500">
              {fetchReturn.errors.choiceA}
            </p>
          ) : null}
        </div>
        <div className="relative flex flex-col">
          <span className="europa text-[20px]">Choice B</span>
          <input className="input-admin" ref={choiceBRef} type="text"></input>
          {fetchReturn?.errors?.choiceB ? (
            <p className="absolute bottom-[-10px] right-0  text-[10px] text-red-500">
              {fetchReturn.errors.choiceB}
            </p>
          ) : null}
        </div>
        <div className="relative flex flex-col">
          <span className="europa text-[20px]">Choice C</span>
          <input className="input-admin" ref={choiceCRef} type="text"></input>
          {fetchReturn?.errors?.choiceC ? (
            <p className="absolute bottom-[-10px] right-0  text-[10px] text-red-500">
              {fetchReturn.errors.choiceC}
            </p>
          ) : null}
        </div>
        <div className="relative flex flex-col">
          <span className="europa text-[20px]">Answer</span>
          <input className="input-admin" ref={answerRef} type="text"></input>
          {fetchReturn?.errors?.answer ? (
            <p className="absolute bottom-[-10px] right-0  text-[10px] text-red-500">
              {fetchReturn.errors.answer}
            </p>
          ) : null}
        </div>
        <div className="flex justify-end mt-[20px]">
          <Button
            onClick={addQuestion}
            className="ml-[20px] bg-primary text-white"
          >
            Add
          </Button>
        </div>
        {fetchReturn?.errors?.match ? (
          <p className="text-red-500">{fetchReturn.errors.match}</p>
        ) : null}
        {questionAdd ? (
          <>
            <p className="text-green-500">Question Added</p>{" "}
            <Button
              onClick={clearInput}
              className="mt-[20px] bg-primary text-white w-fit"
            >
              Clear input
            </Button>
            <Link
              className="mt-[20px] underline hover:text-secondary"
              to="/admin/questions"
            >
              go to question
            </Link>
          </>
        ) : null}
      </div>
    </div>
  );
};

export default AddQuestion;
