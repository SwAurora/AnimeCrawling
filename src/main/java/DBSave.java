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
            for(List<AnimationInfoDTO> infoDTOs : AllResult)
            {
                for(AnimationInfoDTO infoDTO : infoDTOs)
                {
                    String sql = "insert into ANIME(title, thumb, originaltitle, director, production, genre, classification, airingyear, airingdate, grade, episodes) values(?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, infoDTO.getTitle());
                    pstmt.setString(2, infoDTO.getThumb());
                    pstmt.setString(3, infoDTO.getOriginalTitle());
                    pstmt.setString(4, infoDTO.getDirector());
                    pstmt.setString(5, infoDTO.getProduction());
                    pstmt.setString(6, infoDTO.getGenre());
                    pstmt.setString(7, infoDTO.getClassification());
                    pstmt.setInt(8, infoDTO.getAiringYear());
                    pstmt.setString(9, infoDTO.getAiringDate());
                    pstmt.setString(10, infoDTO.getGrade());
                    pstmt.setString(11, infoDTO.getEpisodes());
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
