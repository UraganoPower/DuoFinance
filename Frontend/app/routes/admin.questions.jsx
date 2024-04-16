import { StatusCodes } from "http-status-codes";
import { useEffect, useState } from "react";
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

const QuestionsAdmin = () => {
  const [questions, setQuestions] = useState([]);

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
                  <button className="ml-[20px] mr-[20px]">
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
