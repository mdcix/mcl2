import java.util.*;
public class rcfour{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int a=sc.nextInt();
        int b=sc.nextInt();
        int [] str=new int[a];
        

        System.out.println("enter s elements");
        for(int i=0;i<a;i++){
           str[i]=sc.nextInt();
        }
         int[] key=new int[b];
        System.out.println("enter k elements");
        for(int i=0;i<b;i++){
           key[i]=sc.nextInt();
        }
        
        int len=str.length;
        int lkey=key.length;
        int[] gen=new int[len];
        for(int i=0;i<len;i++){
            gen[i]=key[i%lkey];
            System.out.println(gen[i]);


        }
        int j=0;
        int temp=0;
        for(int i=0;i<len;i++){
            j=(j+str[i]+gen[i])%len;
            temp=str[i];
            str[i]=str[j];
            str[j]=temp;
            for(int k=0;k<len;k++){
                System.out.print(str[k]);
                
            }
            
            System.out.println();
        }
        System.out.println("final:"+Arrays.toString(str));
        fun1(str);
        

        
    }
    public static void fun1(int[] s){
        int i=0;
        int j=0;
        Scanner input=new Scanner(System.in);
        System.out.println("Enter plain text len:");
        int plain_len=input.nextInt();
        int[] plain_txt=new int[plain_len];
        for(int y=0;y<plain_len;y++){
            plain_txt[y]=input.nextInt();
        }
        System.out.println("plain text: "+ Arrays.toString(plain_txt));
        ArrayList<Integer> k=new ArrayList<>();
        int cnt=plain_len;
        while(cnt > 0){
            i=(i+1)%s.length;
            j=(j+s[i])%s.length;
            int temp=0;
            temp=s[i];
            s[i]=s[j];
            s[j]=temp;
            int ct=(s[i]+s[j])%s.length;
            k.add(s[ct]);
            cnt--;
        }
        int[] rlt=new int[k.size()];
        for(int x=0;x<k.size();x++){
            rlt[x]=k.get(x);
        }
        int[] result=new int[rlt.length];
        for(int w=0;w<rlt.length;w++){
            result[w]=plain_txt[w]^rlt[w];
        }
         System.out.println("new k:"+Arrays.toString(rlt));
        System.out.println("The Encrypted text: " + Arrays.toString(result));


        int[]result2=new int[result.length];
        for(int b=0;b<result.length;b++){
            result2[b]=result[b]^rlt[b];
        }
        System.out.println("The decrypted text: " + Arrays.toString(result2));
        return ;
    }

}