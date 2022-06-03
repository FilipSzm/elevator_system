
const ElevatorBox = () => {
    return (
        <div className="elevatorBox withElevator">
            <button className="boxButton">add target</button>
            <button className="boxButton">teleport</button>
        </div>
    );
}


export default function BuildingState(data) {


    return (
        <div>
            <ElevatorBox xd={5}/>
            <ElevatorBox />
            <ElevatorBox />
            <ElevatorBox />
            <ElevatorBox />
            <ElevatorBox />
            <ElevatorBox />
            <ElevatorBox />
        </div>
    );
}