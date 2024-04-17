import { StatusCodes } from "http-status-codes";
import { useEffect, useRef, useState } from "react";
import {
  Table,
  TableHeader,
  TableBody,
  TableColumn,
  TableRow,
  TableCell,
} from "@nextui-org/table";
import DotColumn from "../svg/dot-column";
import TrashSvg from "../svg/trashSvg";
import EditSvg from "../svg/editSvg";
import { Button } from "@nextui-org/button";

const QuestionsAdmin = () => {
  const [questions, setQuestions] = useState([]);

  const modalRef = useRef();
  const [idState, setIdState] = useState();
  const questionRef = useRef();
  const answerRef = useRef();
  const choiceARef = useRef();
  const choiceBRef = useRef();
  const choiceCRef = useRef();

  const openModal = (id, question, answer, choiceA, choiceB, choiceC) => {
    setIdState(id);
    questionRef.current.value = question;
    answerRef.current.value = answer;
    choiceARef.current.value = choiceA;
    choiceBRef.current.value = choiceB;
    choiceCRef.current.value = choiceC;
    modalRef.current.showModal();
  };

  const closeModal = () => {
    modalRef.current.close();
  };

  useEffect(() => {
    fetchQuestions();
  }, []);

  const fetchQuestions = () => {
    fetch("http://localhost:8080/api/question", {
      method: "Get",
      credentials: "include",
    })
      .then((res) => {
        if (res.status !== StatusCodes.OK) {
          throw new Error("Cant fetch questions for some reason");
        }
        return res.json();
      })
      .then((data) => {
        console.log(data);
        setQuestions(data);
      });
  };

  const editQuestion = () => {
    fetch(`http://localhost:8080/api/question`, {
      method: "Put",
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
        questionId: idState,
      }),
    }).then((res) => {
      if (res.status !== StatusCodes.OK) {
        throw new Error("Cant edit questions for some reason");
      }
      closeModal();
      fetchQuestions();
    });
  };

  const deleteQuestion = (id) => {
    fetch(`http://localhost:8080/api/question/${id}`, {
      method: "Delete",
      credentials: "include",
    })
      .then((res) => {
        if (res.status !== StatusCodes.OK) {
          throw new Error("Cant delete questions for some reason");
        }
      })
      .then(() => {
        fetchQuestions();
      });
  };

  return (
    <div className="flex justify-center w-full px-[50px] overflow-auto removeScrollBar">
      <dialog ref={modalRef} className="modal ">
        <h1 className="europa text-[30px]">Edit Questions</h1>
        <div className="flex flex-col mt-[20px]">
          <span className="europa text-[20px]">Question</span>
          <textarea ref={questionRef} className="text-area-admin"></textarea>
          <span className="europa text-[20px]">Answer</span>
          <input className="input-admin" ref={answerRef} type="text"></input>
          <span className="europa text-[20px]">Choice A</span>
          <input className="input-admin" ref={choiceARef} type="text"></input>
          <span className="europa text-[20px]">Choice B</span>
          <input className="input-admin" ref={choiceBRef} type="text"></input>
          <span className="europa text-[20px]">Choice C</span>
          <input className="input-admin" ref={choiceCRef} type="text"></input>
        </div>
        <div className="flex justify-end mt-[20px]">
          <Button onClick={closeModal} className="bg-red-500 text-white">
            Close
          </Button>
          <Button
            onClick={editQuestion}
            className="ml-[20px] bg-primary text-white"
          >
            Save
          </Button>
        </div>
      </dialog>
      <Table className="mt-[50px] border-spacing-y-[20px]">
        <TableHeader className="text-left rounded-lg ">
          <TableColumn className="table-column-admin europa rounded-tl-[15px]">
            QuestionId
          </TableColumn>
          <TableColumn className="table-column-admin europa">
            Question
          </TableColumn>
          <TableColumn className="table-column-admin europa">
            Answer
          </TableColumn>
          <TableColumn className="table-column-admin europa">
            Choice A
          </TableColumn>
          <TableColumn className="table-column-admin europa">
            Choice B
          </TableColumn>
          <TableColumn className="table-column-admin europa">
            Choice C
          </TableColumn>
          <TableColumn className="table-column-admin europa rounded-tr-[15px]">
            Options
          </TableColumn>
        </TableHeader>
        <TableBody>
          {questions.map((question, index) => (
            <TableRow key={`question-${index}`} className="table-row-admin">
              <TableCell className="table-cell-admin ">
                <span className="ml-[20px]">{question.questionId}</span>
              </TableCell>
              <TableCell className="table-cell-admin">
                {question.questionText}
              </TableCell>
              <TableCell className="table-cell-admin">
                {question.answer}
              </TableCell>
              <TableCell className="table-cell-admin">
                {question.choiceA}
              </TableCell>
              <TableCell className="table-cell-admin">
                {question.choiceB}
              </TableCell>
              <TableCell className="table-cell-admin">
                {question.choiceC}
              </TableCell>
              <TableCell className="table-cell-admin">
                <div className="flex">
                  <button onClick={() => deleteQuestion(question.questionId)}>
                    <TrashSvg
                      className={"stroke-red-500 hover:stroke-red-600"}
                    ></TrashSvg>
                  </button>
                  <button
                    onClick={() =>
                      openModal(
                        question.questionId,
                        question.questionText,
                        question.answer,
                        question.choiceA,
                        question.choiceB,
                        question.choiceC
                      )
                    }
                    className="ml-[20px] mr-[20px]"
                  >
                    <EditSvg></EditSvg>
                  </button>
                </div>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
};
export default QuestionsAdmin;
