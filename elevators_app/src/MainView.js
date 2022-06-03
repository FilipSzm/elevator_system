import './MainView.css';
import {useState} from "react";
import BuildingState from "./BuildingState";




const PickupButton = () => {


    return (
        <div className="pickupButton">
            <button className="boxButton">add pickup</button>
        </div>
    );
}

export default function MainView(initialState) {
    const [buildingState, setBuildingState] = useState(initialState)



    function amogus() {


        setBuildingState(undefined)
    }

    return (
        <div className="mainContainer">
            <div>
                <PickupButton onClick={() => amogus(1)}/>
                <PickupButton />
                <PickupButton />
                <PickupButton />
                <PickupButton />
                <PickupButton />
                <PickupButton />
                <PickupButton />
            </div>

            <div>
                <BuildingState data={buildingState}/>
            </div>


        </div>
    );
}