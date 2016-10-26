import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jo930_000 on 2016-10-24.
 */
public class CountingInversion {
    ArrayList<Integer> Inver_List = new ArrayList<Integer>();

    public void CountingInversion() throws IOException {

        FileInputStream input = new FileInputStream("C:/Users/jo930_000/IdeaProjects/Algorithm_hw06/src/data07_inversion.txt");
        InputStreamReader reader = new InputStreamReader(input);
        StreamTokenizer token = new StreamTokenizer(reader);

        while((token.nextToken() != StreamTokenizer.TT_EOF)) {
            switch(token.ttype){
                case StreamTokenizer.TT_NUMBER :
                    Inver_List.add((int) token.nval);
                    break;
            }
        }

        input.close();

        // 정렬
        this.sort_and_count(Inver_List).listPrint();

    }

    public class ListAndCount {
        int inversion_count;
        ArrayList<Integer> List;

        public ListAndCount() {
            this.inversion_count = 0;
            this.List = new ArrayList<>();
        }

        public ListAndCount(int count, ArrayList<Integer> List) {
            this.inversion_count = count;
            this.List = List;
        }

        public void listPrint() {
            for(int i = 0; i < List.size()-1; i++) {
                System.out.print(List.get(i)+", ");
            }
            System.out.print(List.get(List.size()-1) + "\tinversion count : " + this.inversion_count +"\n");
        }
    }

    public ListAndCount sort_and_count(ArrayList<Integer> L) {
        if(L.size() == 1) return new ListAndCount(0, L);

        ArrayList<Integer> A = new ArrayList<>();
        ArrayList<Integer> B = new ArrayList<>();

        int mid = L.size()/2;
        for(int i = 0; i <mid; i++)  A.add(L.get(i));
        for(int i = mid; i < L.size(); i++)  B.add(L.get(i));

        ListAndCount AL = sort_and_count(A);
        ListAndCount BL = sort_and_count(B);
        ListAndCount LL = merge_and_count(AL.List,BL.List);

        return new ListAndCount(AL.inversion_count+BL.inversion_count+LL.inversion_count, LL.List);
    }

    public ListAndCount merge_and_count(ArrayList<Integer> A, ArrayList<Integer> B) {
        int inversion_count = 0;
        int index_A = 0;
        int index_B = 0;
        ArrayList<Integer> L = new ArrayList<>();
        while(A.size() != 0 && B.size() != 0) {
            if(A.get(index_A) > B.get(index_B)) {
                inversion_count = inversion_count + A.size();
                L.add(B.remove(index_B));
            }
            else {
                L.add(A.remove(index_A));
            }
        }

        while(A.size() > 0) L.add(A.remove(0));
        while(B.size() > 0) L.add(B.remove(0));

        return new ListAndCount(inversion_count, L);
    }
}
