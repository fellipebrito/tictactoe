(ns tic-tac-toe-2.core-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe-2.core :refer :all]))

(deftest new-mark-test
  (testing "Adds a new mark to a new game"
    (let [game             new-game
          game-after-mark  (new-mark game :one 0)
          board-after-mark (get-in game-after-mark [:board])
          player-one-marks (get-in game-after-mark [:players :one :marks])]
      (is (= board-after-mark [1 2 3 4 5 6 7 8]))
      (is (= player-one-marks [0]))))
  (testing "Adds a many marks to a new game"
    (let [game              new-game
          game              (new-mark game (get game :turn) 0)
          game              (new-mark game (get game :turn) 1)
          game-after-marks  (new-mark game (get game :turn) 2)
          board-after-marks (get-in game-after-marks [:board])
          player-one-marks  (get-in game-after-marks [:players :one :marks])
          player-two-marks  (get-in game-after-marks [:players :two :marks])]
      (is (= board-after-marks [3 4 5 6 7 8]))
      (is (= player-one-marks [0 2]))
      (is (= player-two-marks [1])))))
