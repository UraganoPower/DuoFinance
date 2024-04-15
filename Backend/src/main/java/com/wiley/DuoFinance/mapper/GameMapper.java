package com.wiley.DuoFinance.mapper;

import com.wiley.DuoFinance.model.Game;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameMapper implements RowMapper<Game> {

    @Override
    public Game mapRow(ResultSet rs, int rowNum) throws SQLException {

        Game game;

        game = new Game();

        game.setGameId(rs.getInt("gameId"));
        game.setScore(rs.getInt("score"));

        return game;
    }
}
