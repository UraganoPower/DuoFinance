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
  const searhRef = useRef();
  const [idToDelete, setIdToDelete] = useState();

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
        closeModalDelete();
        if (res.status !== StatusCodes.OK) {
          throw new Error("Cant delete questions for some reason");
        }
      })
      .then(() => {
        fetchQuestions();
      });
  };

  const searchQuestion = () => {
    fetch(
      `http://localhost:8080/api/question/search/${searhRef.current.value}`,
      {
        method: "Post",
        credentials: "include",
      }
    )
      .then((res) => {
        if (res.status !== StatusCodes.OK) {
          throw new Error("Cant search questions for some reason");
        }
        return res.json();
      })
      .then((data) => {
        setQuestions(data);
      });
  };

  const modalDelete = useRef();
  const openModalDelete = (id) => {
    setIdToDelete(id);
    modalDelete.current.showModal();
  };

  const closeModalDelete = () => {
    modalDelete.current.close();
  };

  return (
    <div className="flex flex-col  w-full px-[50px] overflow-auto removeScrollBar">
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
      <dialog ref={modalDelete} className="modal">
        <p className="mb-[30px] europa">
          Are you sure you want to delete question_Id : {idToDelete} ?
        </p>
        <p className="mb-[30px] text-red-600">This action cannot be undone.</p>
        <Button onClick={closeModalDelete} className="bg-primary text-white">
          Close
        </Button>
        <Button
          onClick={() => deleteQuestion(idToDelete)}
          className="ml-[20px] bg-red-500 text-white"
        >
          Delete
        </Button>
      </dialog>
      <div className="flex mt-[20px]">
        <input ref={searhRef} className="input-admin" type="text"></input>
        <Button
          onClick={searchQuestion}
          className="mx-[20px] bg-primary text-white"
        >
          Search
        </Button>
        <Button onClick={fetchQuestions} className="bg-accent text-white">
          All
        </Button>
      </div>
      <Table className=" border-spacing-y-[20px]">
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
                  <button onClick={() => openModalDelete(question.questionId)}>
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
