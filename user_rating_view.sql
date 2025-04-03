DROP TABLE IF EXISTS user_rating;

CREATE OR REPLACE VIEW user_rating AS
SELECT
    users.id AS user_id,
    ((IFNULL(SUM(players.rating), 0) + users.card_rating) / 11) AS lineup_rating
FROM
    users
    LEFT JOIN owned_players ON users.id = owned_players.user_id
    LEFT JOIN players ON players.id = owned_players.player_id
GROUP BY
    users.id;