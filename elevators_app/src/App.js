import './App.css';
import MainView from "./MainView";

function App() {

    function initBuilding() {

    }


    return (
    <div>
      <MainView initialState={initBuilding()}/>
    </div>
    );
}

export default App;
