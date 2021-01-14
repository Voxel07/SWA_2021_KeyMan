import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter } from "react-router-dom";
import "./index.css";
import App from "./App";
import MyComponent from "./components/MyComponent";
import * as serviceWorker from "./serviceWorker";
import GET from "./components/MyGetComponent"

ReactDOM.render(
    <BrowserRouter>
        <MyComponent />
        <GET/>
    </BrowserRouter>,
    document.getElementById("root")
);

serviceWorker.unregister();