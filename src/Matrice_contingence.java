



    import java.io.BufferedReader;

    import java.io.IOException;

    public class Matrice_contingence {

       protected int num_variables,num_clauses;
       protected String path;
       protected BufferedReader reader;
       protected int[][] matrice;

       // public Matrice_contingence(String path){ }
        public Matrice_contingence(int num_variables, int num_clauses, BufferedReader reader) throws IOException {
            this.num_variables=num_variables;
            this.num_clauses=num_clauses;
            this.reader=reader;
            this.matrice=new int [num_clauses][num_variables];
            int j=0;String line;
            int[] a=new int[num_clauses];

            for(int i=0;i<num_clauses;i++){
                for(j=0;j<num_variables;j++){
                this.matrice[i][j]=0;}}

          /*  System.out.println("affichage de la matrice :");
            for (int r=0;r<num_variables;r++){
                System.out.print("affichage de la variable "+(r+1)+" :  ");
                for (int k=0;k<num_clauses;k++){
                    System.out.print(" "+matrice[r][k]);
                }
                System.out.println("");
            }
    */
                j=0;
                while(j<num_clauses && (line = reader.readLine()) != null){
                    if(line.length()>0 && line.charAt(0) == ' ')
                        line = line.substring(1);
                    String sLine[] = line.split("\\s+");
                    for(int i=0;i<sLine.length;i++){
                       int index=(Integer.parseInt(sLine[i])>0) ? 1:(Integer.parseInt(sLine[i])==0) ? 0:-1;

                       if(index!=0){

                        this.matrice[j][Math.abs(Integer.parseInt(sLine[i]))-1]=index;

                       }

                    }

                   j++;
                }


        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getNum_clauses() {
            return num_clauses;
        }

        public int getNum_variables() {
            return num_variables;
        }

        public void setNum_clauses(int num_clauses) {
            this.num_clauses = num_clauses;
        }

        public void setNum_variables(int num_variables) {
            this.num_variables = num_variables;
        }

        public int[][] getMatrice() {
            return matrice;
        }

        public void setMatrice(int[][] matrice) {
            this.matrice = matrice;
        }

        public String affichage(){

            System.out.println("affichage de la matrice :");
            String clause="";
            for (int i=0;i<this.num_clauses;i++){
                clause+=" clause "+(i+1)+" :  ";
                for (int j=0;j<this.num_variables;j++){
                    if(this.matrice[i][j]==1)
                        clause+=j+" V";
                    else if(this.matrice[i][j]==-1)
                        clause+=(j*-1)+" V ";
                }
                clause=clause.substring(0,clause.length()-2);
                clause+="\n";
            }
            System.out.println(clause);
            return clause;
        }
    }
