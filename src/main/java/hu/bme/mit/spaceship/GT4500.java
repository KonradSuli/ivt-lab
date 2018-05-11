package hu.bme.mit.spaceship;

/**
* A simple spaceship with two proton torpedo stores and four lasers
*/
public class GT4500 implements SpaceShip {

  private TorpedoStore primaryTorpedoStore;
  private TorpedoStore secondaryTorpedoStore;

  private boolean wasPrimaryFiredLast = false;

  public GT4500() {
    this.primaryTorpedoStore = new TorpedoStore(10);
    this.secondaryTorpedoStore = new TorpedoStore(10);
  }

  public GT4500(TorpedoStore pts, TorpedoStore sts) {
    this.primaryTorpedoStore = pts;
    this.secondaryTorpedoStore = sts;
  }

  public boolean fireLaser(FiringMode firingMode) {
    // TODO not implemented yet
    return false;
  }
  /*
  *Tries to fire primary first, if fails it then tries to fire the secondary
  */
  private boolean tryToFirePrimaryFirst() {
    boolean firingSuccess = false;

    if (! primaryTorpedoStore.isEmpty()) {
          firingSuccess = primaryTorpedoStore.fire(1);
          wasPrimaryFiredLast = true;
        }
        else {
          // although secondary was fired last time, but primary is empty
          // thus try to fire secondary again
          if (! secondaryTorpedoStore.isEmpty()) {
            firingSuccess = secondaryTorpedoStore.fire(1);
            wasPrimaryFiredLast = false;
          }

          // if both of the stores are empty, nothing can be done, return failure
        }
  return firingSuccess;
  }
  /*
  *Tries to fire secondary first, if fails it then tries to fire the primary
  */
  private boolean tryToFireSecondaryFirst() {
    boolean firingSuccess = false;
    //Trying to fire secondary
    if (! secondaryTorpedoStore.isEmpty()) {
      firingSuccess = secondaryTorpedoStore.fire(1);
      wasPrimaryFiredLast = false;
    }
    else {
      // although primary was fired last time, but the secondary is empty
      // thus try to fire primary again
      if (! primaryTorpedoStore.isEmpty()) {
        firingSuccess = primaryTorpedoStore.fire(1);
        wasPrimaryFiredLast = true;
      }

      // if both of the stores are empty, nothing can be done, return failure
    }
  return firingSuccess;
  }

  /**
  * Tries to fire the torpedo stores of the ship.
  *
  * @param firingMode how many torpedo bays to fire
  * 	SINGLE: fires only one of the bays.
  * 			- For the first time the primary store is fired.
  * 			- To give some cooling time to the torpedo stores, torpedo stores are fired alternating.
  * 			- But if the store next in line is empty, the ship tries to fire the other store.
  * 			- If the fired store reports a failure, the ship does not try to fire the other one.
  * 	ALL:	tries to fire both of the torpedo stores.
  *
  * @return whether at least one torpedo was fired successfully
  */
  @Override
  public boolean fireTorpedo(FiringMode firingMode) {

    boolean firingSuccess = false;

    if (firingMode == FiringMode.SINGLE) {
      if (wasPrimaryFiredLast) {
        // try to fire the secondary first
        firingSuccess = tryToFireSecondaryFirst();
      }
      else {
        // try to fire the primary first
        firingSuccess = tryToFirePrimaryFirst();
      }
    }

    if (firingMode == FiringMode.ALL) {
      boolean firingSuccessPrimary = false;
      if (!primaryTorpedoStore.isEmpty()) firingSuccessPrimary = primaryTorpedoStore.fire(1);

      boolean firingSuccessSecondary = false;
      if (!secondaryTorpedoStore.isEmpty()) firingSuccessSecondary = secondaryTorpedoStore.fire(1);

      firingSuccess = firingSuccessPrimary || firingSuccessSecondary;
    }

    return firingSuccess;
  }

}
