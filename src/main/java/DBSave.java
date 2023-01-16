import connection.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DBSave
{
    public void save(List<List<AnimationInfoDTO>> AllResult)
    {
        Connection conn = DBConnectionUtil.getConnection();
        try
        {
            for(int i = 0; i < AllResult.size(); i++)
            {
                for(int j = 0; j < AllResult.get(i).size(); j++)
                {
                    AnimationInfoDTO infoDTO = AllResult.get(i).get(j);
                    String dbName = "ANIME_" + (2011 + i);
                    String sql = "insert into " + dbName + "(title, thumb, originaltitle, director, production, genre, classification, airingdate, grade, episodes) values(?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, infoDTO.getTitle());
                    pstmt.setString(2, infoDTO.getThumb());
                    pstmt.setString(3, infoDTO.getOriginalTitle());
                    pstmt.setString(4, infoDTO.getDirector());
                    pstmt.setString(5, infoDTO.getProduction());
                    pstmt.setString(6, infoDTO.getGenre());
                    pstmt.setString(7, infoDTO.getClassification());
                    pstmt.setString(8, infoDTO.getAiringDate());
                    pstmt.setString(9, infoDTO.getGrade());
                    pstmt.setString(10, infoDTO.getEpisodes());
                    pstmt.executeUpdate();
                }
            }
        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}
