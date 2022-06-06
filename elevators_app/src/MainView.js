import './MainView.css';
import {useEffect, useState} from "react";
import BuildingState from "./BuildingState";
import axios from "axios";




const PickupButton = ({floorNumber, pickupFunc}) => {
  return (
    <div className="pickupButton">
      <button className="boxButton" onClick={() => pickupFunc(floorNumber, 'UP')}>pickup UP</button>
      <button className="boxButton" onClick={() => pickupFunc(floorNumber, 'DOWN')}>pickup DOWN</button>
    </div>
  );
}

const StepButton = ({stepFunc}) => {
  return (
    <div className="stepButton">
      <button className="boxButton" onClick={() => stepFunc()}>step</button>
    </div>
  );
}

export default function MainView() {
  const [statusList, setStatusList] = useState([]);


  useEffect(() => {
    axios.get('http://localhost:8080/api/vis/status')
      .then(res => {
        setStatusList(res.data.map(x => [x.id, x.currentFloorNumber]));
      })
  },[]);


  function pickup(id, dir) {
    let direction = {direction: dir};
    axios.put('http://localhost:8080/api/vis/pickup/' + id, direction)
  }

  async function teleport(id, floorNum) {
    let floor = {floorNumber: floorNum}
    await axios.patch('http://localhost:8080/api/vis/update/' + id, floor)

    axios.get('http://localhost:8080/api/vis/status')
      .then(res => {
        setStatusList(res.data.map(x => [x.id, x.currentFloorNumber]));
      })
  }

  function target(id, floorNum) {
    let floor = {floorNumber: floorNum}
    axios.put('http://localhost:8080/api/vis/target/' + id, floor)
  }

  async function step() {
    await axios.put('http://localhost:8080/api/vis/step')

    axios.get('http://localhost:8080/api/vis/status')
      .then(res => {
        setStatusList(res.data.map(x => [x.id, x.currentFloorNumber]));
      })
  }

  const floors = [0,-1,-2,-3,-4,-5,-6,-7,-8,-9,-10]

  return (
    <div className="mainContainer">
      <div>
        {floors.map(n =>
          <PickupButton key={n} floorNumber={n} pickupFunc={pickup}/>
        )}
      </div>

      <BuildingState data={statusList}
                     teleportFunc={teleport}
                     targetFunc={target}
                     floors={floors}
      />

      <StepButton stepFunc={step}/>
    </div>
  );
}