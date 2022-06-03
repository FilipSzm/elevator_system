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
    <div>
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

  function teleport(id, floorNum) {
    let floor = {floorNumber: floorNum}
    axios.patch('http://localhost:8080/api/vis/update/' + id, floor)

    axios.get('http://localhost:8080/api/vis/status')
      .then(res => {
        setStatusList(res.data.map(x => [x.id, x.currentFloorNumber]));
      })
  }

  function target(id, floorNum) {
    let floor = {floorNumber: floorNum}
    axios.put('http://localhost:8080/api/vis/target/' + id, floor)
  }

  function step() {
    axios.put('http://localhost:8080/api/vis/step')

    axios.get('http://localhost:8080/api/vis/status')
      .then(res => {
        setStatusList(res.data.map(x => [x.id, x.currentFloorNumber]));
      })
  }

  return (
    <div className="mainContainer">
      <div>
        {[10,9,8,7,6,5,4,3,2,1,0].map(n =>
          <PickupButton key={n} floorNumber={n} pickupFunc={pickup}/>
        )}
      </div>

      <BuildingState data={statusList} teleportFunc={teleport} targetFunc={target}/>

      <StepButton stepFunc={step}/>
    </div>
  );
}