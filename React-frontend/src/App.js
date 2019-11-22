import React from "react";
import { Provider } from "react-redux";
import "./App.css";
import BookComponent from "./containers/BookContainer";
import storeConfig from "./store/storeConfig";

function App() {
  return (
    <Provider store={storeConfig}>
      <BookComponent />
    </Provider>
  );
}

export default App;
