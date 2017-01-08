(ns tic-tac-toe-2.core
  (:gen-class))

(def empty-board (vec (range 0 9)))

(def win-sets
  [[0 1 2], [3 4 5], [6 7 8], [0 3 6], [1 4 7], [2 5 8], [0 4 8], [2 4 6]])

(def players
  {:one {:mark  "X"
         :marks []
         :type  :human}
   :two {:mark  "O"
         :marks []
         :type  :human}})

(def new-game
  {:board   empty-board
   :players players
   :turn    :one})

;; PRINT LOGIC!
(defn board-suffix [x]
  (cond
    (> x 7)            nil
    (= 2 (mod x 3))    "\n-----------\n"
    :else              "|"))

(defn board-str [board]
  (apply str
         (map #(str " " (nth board %) " " (board-suffix %))
              (vec (range 9)))))

(defn read-int
  "Waits for an user input and tests if it is an integer"
  []
  (try (Integer/parseInt (read-line))
       (catch NumberFormatException e nil)))

(defn valid-move?
  "Tests if the integer mark-at is still an open space on the board"
  [game mark-at]
  (integer? (nth (:board game) mark-at)))

(defn indexes-of [e coll] (keep-indexed #(if (= e %2) %1) coll))

(defn game-over? [board]
  (let [X     (indexes-of "X" board)
        O     (indexes-of "O" board)
        x-set (map #(every? (set X) (vec %)) win-sets)
        o-set (map #(every? (set O) (vec %)) win-sets)]
    (or (and (not (empty? x-set)) (some true? x-set))
        (and (not (empty? o-set)) (some true? o-set)))))
;; (= 0 (count (filter integer? board))))

(defn new-mark [game mark-at]
  (if (valid-move? game mark-at)
    (let [current-player (get game :turn)
          ;; Remove index from board
          game (assoc-in game [:board]
                         (assoc (get game :board)
                                mark-at
                                (get-in game [:players current-player :mark])))
          ;; adds index to player marks
          game (assoc-in game [:players current-player :marks]
                         (conj (get-in game [:players current-player :marks]) mark-at))
          ;; changes the active player
          game (assoc-in game [:turn]
                         (if (= :one current-player) :two :one))]
      game)
    game))

(defn play
  [game]
  (if (game-over? (:board game))
    (do
      (println (str "Winner: " (get-in game [:players (if (= :one (:turn game)) :two :one) :mark]))))
    (do
      (println (str (board-str (:board game))))
      (recur (new-mark game (read-int))))))

(defn -main
  [& args]
  (play new-game))


;; Validate if it is empty (Test the O... it will return true, and it is wrong)
;; (let [game new-game
;;       game  (new-mark game 0)
;;       game  (new-mark game 4)
;;       game  (new-mark game 1)
;;       game  (new-mark game 8)
;;       game  (new-mark game 2)
;;       X     (indexes-of "X" (:board game))
;;       O     (indexes-of "O" (:board game))
;;       x-set (map #(every? (set X) (vec %)) win-sets)
;;       o-set (map #(every? (set O) (vec %)) win-sets)]
;;   ;; (and (not (empty? o-set)) (some true? o-set)))
;;   x-set)
