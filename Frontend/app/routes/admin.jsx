import { Button } from "@nextui-org/button";
import { Link, Outlet, useNavigate } from "@remix-run/react";
import { StatusCodes } from "http-status-codes";
import { useEffect } from "react";
import style from "~/styles/admin.css";

export const links = () => [{ rel: "stylesheet", href: style }];

const Admin = () => {
  const navigate = useNavigate();

  useEffect(() => {
    fetch("http://localhost:8080/api/user", {
      method: "Get",
      credentials: "include",
    }).then((res) => {
      if (res.status !== StatusCodes.OK) {
        navigate("/login");
      }
      res.json().then((data) => {
        if (data.roleId == 2) {
          navigate("/login");
        }
      });
    });
  }, []);

  const logout = () => {
    fetch("http://localhost:8080/api/logout", {
      method: "POST",
      credentials: "include",
    }).then((res) => {
      if (res.status === StatusCodes.OK) {
        navigate("/login");
      }
    });
  };

  return (
    <div className="flex h-[100vh]">
      <nav>
        <div className="title">
          <h1 className="europa">Duo Finance</h1>
          <h2 className="eurostyle">Admin panel</h2>
        </div>
        <div className="flex flex-col justify-between h-full">
          <ul className="eurostyle">
            <Link to={"./questions"}>
              <li>Questions</li>
            </Link>
            <Link to={"./add"}>
              <li>Add question</li>
            </Link>
          </ul>
          <Button
            onClick={logout}
            className="eurostyle mx-[20px] mb-[20px] bg-secondary text-white text-[20px]"
          >
            Log out
          </Button>
        </div>
      </nav>
      <Outlet></Outlet>
    </div>
  );
};

export default Admin;
