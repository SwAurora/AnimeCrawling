import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class CrawlingLogic
{
    public List<AnimationInfoDTO> getList(int year) throws IOException
    {
        List<AnimationInfoDTO> list = new ArrayList<>();
        AnimationInfoDTO infoDTO;

        String pageCheckUrl = "https://ohlitv.com/bbs/board.php?bo_table=fin&sca=" + year;
        Document pageCheckdoc = Jsoup.connect(pageCheckUrl).get();
        Elements page = pageCheckdoc.select("ul.pagination > li > a");
        int totalPage = page.size() - 4;

        for(int j = 1; j <= totalPage; j++)
        {
            String titleUrl = "https://ohlitv.com/bbs/board.php?bo_table=fin&sca=" + year + "&page=" + j;
            Document doc = Jsoup.connect(titleUrl).get();

            Elements titles = doc.select("div.post-title");
            Elements Epi = doc.select("p.pull-right");
            Map<String, String> titles_Epi = new HashMap<>();
            for(int i = 0; i < titles.size(); i++)
            {
                titles_Epi.put(titles.get(i).text(), Epi.get(i).text());
            }

            for(Element title : titles)
            {
                String infoUrl = "https://ohlitv.com/c/" + title.text();
                infoUrl = infoUrl.replace("[", "%5B").replace("]", "%5D");
                if(title.text().equals("오늘부터 신령님 ◎ 2기"))
                {
                    infoUrl = "https://ohlitv.com/c/오늘부터 신령님 ◎%20 2기";
                }
                if(title.text().equals("일하는 형씨! 2기"))
                {
                    infoUrl = "https://ohlitv.com/c/일하는 형씨!%20 2기";
                }

                Document doc2 = Jsoup.connect(infoUrl).get();
                String thumb;
                if(doc2.select("div.image > div > img").first() != null)
                {
                    thumb = Objects.requireNonNull(doc2.select("div.image > div > img").first()).attr("abs:src");
                }
                else
                {
                    thumb = null;
                }
                Elements infos = doc2.select("div.list > p > span.block");
                Map<String, String> map = new HashMap<>();
                for(int i = 0; i < infos.size(); i++)
                {
                    if(i % 2 == 0)
                    {
                        map.put(infos.get(i).text(), null);
                    }
                    else
                    {
                        map.put(infos.get(i - 1).text(), infos.get(i).text());
                    }
                }

                if(map.get("장르") != null)
                {
                    map.put("장르", map.get("장르").replace(" ", ""));
                }

                infoDTO = new AnimationInfoDTO();
                infoDTO.setTitle(title.text());
                infoDTO.setThumb(thumb);
                infoDTO.setOriginalTitle(map.get("원제"));
                infoDTO.setDirector(map.get("감독"));
                infoDTO.setProduction(map.get("제작사"));
                infoDTO.setGenre(map.get("장르"));
                infoDTO.setClassification(map.get("분류"));
                infoDTO.setAiringYear(year);
                infoDTO.setAiringDate(map.get("방영일"));
                infoDTO.setGrade(map.get("등급"));
                infoDTO.setEpisodes(titles_Epi.get(title.text()));
                list.add(infoDTO);
            }
        }

        return list;
    }
}
