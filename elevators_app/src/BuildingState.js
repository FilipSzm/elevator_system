
const ElevatorBox = ({classes, targetFunc, teleportFunc, floorNumber, elevatorId}) => {
  return (
    <div className={classes}>
      <button className="boxButton" onClick={() => targetFunc(elevatorId, floorNumber)}>add target</button>
      <button className="boxButton" onClick={() => teleportFunc(elevatorId, floorNumber)}>teleport</button>
    </div>
  );
}


export default function BuildingState({data, targetFunc, teleportFunc}) {

  return (
    <div className="boxesContainer">
      {data.map(x =>
        <div key={x[0]}>{[10,9,8,7,6,5,4,3,2,1,0]
          .map(n =>
            <ElevatorBox
              key={n}
              classes={n === x[1] ? "elevatorBox withElevator" : "elevatorBox"}
              targetFunc={targetFunc}
              teleportFunc={teleportFunc}
              floorNumber={n}
              elevatorId={x[0]}/>)}
        </div>)
      }
    </div>
  );
}