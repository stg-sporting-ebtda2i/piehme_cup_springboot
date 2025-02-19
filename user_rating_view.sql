CREATE VIEW user_rating AS
Select users.id                                                                                        as user_id,
       ((IFNULL(sum(players.rating), 0) + users.card_rating) / (IFNULL(count(players.rating), 0) + 1)) as lineup_rating
from users
         left join owned_players on users.id = owned_players.user_id
         left join players on players.id = owned_players.player_id

where players.`position_id` != users.position_id
   or players.position_id is null

group by users.id