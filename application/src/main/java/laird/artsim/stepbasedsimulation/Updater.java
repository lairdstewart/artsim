package laird.artsim.stepbasedsimulation;

public abstract class Updater <S extends State> {
    public abstract S update(S state);
}
