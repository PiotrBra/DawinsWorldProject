package Observers;

import Simulations.SimulationEngine;
import UI.StartApp;

public class Controller implements SimulationObserver {
    private final StartApp view;

    public Controller(SimulationEngine model, StartApp view) {
        this.view = view;
        model.setObserver(this);
    }

    @Override
    public void SimulationStep() {
        view.uploadMap();
    }
}