import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application
{
    public static void main(String[] args) throws IOException
    {
        CrawlingLogic logic = new CrawlingLogic();
        DBSave db = new DBSave();
        List<List<AnimationInfoDTO>> AllResult = new ArrayList<>();

        int startYear = 2011;
        int endYear = 2023;

        System.out.println("CRAWLING START...");
        for(int i = startYear; i <= endYear; i++)
        {
            List<AnimationInfoDTO> list = logic.getList(i);
            AllResult.add(list);
        }
        System.out.println("CRAWLING COMPLETE");

        System.out.println("SAVE START...");
        db.save(AllResult);
        System.out.println("SAVE COMPLETE");
    }
}
