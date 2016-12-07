package com.groupone.p2pgame;

import java.io.Serializable;

/**
   Player holds identifiers for each player. This is used to store
   data within the GameBoard.
   @see CheckerBoardState
 */
public enum Player implements Serializable {
    /**
       Default value when space is empty.
    */
    NONE,

    /**
       Player one will have this.
    */
    ONE,

    /**
       Player two will have this.
     */
    TWO,

    // more?
}
