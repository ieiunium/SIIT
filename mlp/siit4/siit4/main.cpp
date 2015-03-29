#include <iostream>
#include <vector>
#include <cstdlib>
#include <time.h>
#include <algorithm>
#include <fstream>
#include <string>
using namespace std;
int step;
int dataIn[32][5]={
    0, 0, 0, 0, 0,
    1, 0, 0, 0, 0,
    0, 1, 0, 0, 0,
    1, 1, 0, 0, 0,
    0, 0, 1, 0, 0,
    1, 0, 1, 0, 0,
    0, 1, 1, 0, 0,
    1, 1, 1, 0, 0,
    0, 0, 0, 1, 0,
    1, 0, 0, 1, 0,
    0, 1, 0, 1, 0,
    1, 1, 0, 1, 0,
    0, 0, 1, 1, 0,
    1, 0, 1, 1, 0,
    0, 1, 1, 1, 0,
    1, 1, 1, 1, 0,
    0, 0, 0, 0, 1,
    1, 0, 0, 0, 1,
    0, 1, 0, 0, 1,
    1, 1, 0, 0, 1,
    0, 0, 1, 0, 1,
    1, 0, 1, 0, 1,
    0, 1, 1, 0, 1,
    1, 1, 1, 0, 1,
    0, 0, 0, 1, 1,
    1, 0, 0, 1, 1,
    0, 1, 0, 1, 1,
    1, 1, 0, 1, 1,
    0, 0, 1, 1, 1,
    1, 0, 1, 1, 1,
    0, 1, 1, 1, 1,
    1, 1, 1, 1, 1
};
int dataOut[32]={
    1,
    1,

    -1,
    1,
    -1,
    1,
    1,

    -1,
    -1,
    1,
    1,
    -1,

    1,
    -1,
    -1,
    1,
    -1,

    1,
    1,
    -1,
    1,
    -1,

    -1,
    1,
    1,
    -1,
    -1,

    1,
    -1,
    1,
    1,
    -1
};

struct Chromosome{
        vector<double> gens;
        int fitness;
        int calcF();
        Chromosome(){
            int n = 30;

            gens = vector<double>(n);
            for(int i=0;i<n;i++){
                gens[i] = 2.0*(rand()%1000)/1000 - 1;
            }
        }
};
bool myfunction (Chromosome i,Chromosome j) { return (i.fitness>j.fitness); }
int fitnessFunction(Chromosome c);
int Chromosome::calcF(){
    this->fitness = fitnessFunction(*this);
    return fitness;
}

struct NN{
    double hide[25];
    double w[5];
    int getOut(int in[]){
        double y[5];
        double out=0;
        for(int i =0 ;i<5;i++){
            y[i] = 0;
            for(int j =0 ;j<5;j++){
                y[i]+= hide[5*i+j]*in[j];
            }
            if(y[i]>0){
                y[i]=1;
            }else{
                y[i]=-1;
            }
            out+=y[i] * w[i];
        }
        if(out>0){
            return +1;
        }else{
            return -1;
        }
    }
    void setW(Chromosome c){
        for(int i=0;i<25;i++){
            hide[i] = c.gens[i];
        }
        for(int i=0;i<5;i++){
            w[i] = c.gens[i+25];
        }
    }
}nn;
ofstream ftableIn("target/in.txt",std::ios::out);
ofstream ftableEtalonOut("target/eout.txt",std::ios::out);
ofstream ftableOut("target/out.txt",std::ios::out);
ofstream ftableIsOk("target/ok.txt",std::ios::out);
int fitnessFunction(Chromosome c){

    int res=0;
    nn.setW(c);
    for(int i=0;i<32;i++){
        int out = nn.getOut(dataIn[i]);

        if(out==dataOut[i]){
            res++;
        }
    }
    return res;
}

int printTable(Chromosome c,int ii){
    ftableIn <<endl<<endl<<ii<<endl;
    ftableEtalonOut<<endl<<endl<<ii<< endl;
    ftableOut<<endl<<endl<<ii<<endl;
    ftableIsOk<<endl<<endl<<ii<<endl;
    int res=0;
    nn.setW(c);
    for(int i=0;i<32;i++){
        int out = nn.getOut(dataIn[i]);
        if(out==dataOut[i]){
            res++;
            ftableIsOk<<"+"<<endl;
        }else{
            ftableIsOk<<"-"<<endl;
        }

        for (int j = 0; j < 5; ++j) {
            ftableIn << dataIn[i][j] << " ";
        }
        ftableIn << endl;
        ftableEtalonOut<<dataOut[i]<<endl;
        ftableOut<<out<<endl;


    }
    return res;
}
Chromosome XOver(Chromosome c1,Chromosome c2){
    Chromosome tmp;
    int b = rand()%30;
    for(int i = 0; i < 30; i++){
        if(1){//if(rand()%2==0){
            tmp.gens[i] = c1.gens[i];
        }else{
            tmp.gens[i] = c2.gens[i];
        }
        double p =((double)(rand()%100000))/100000.0;

        if( p < 0.02){
            tmp.gens[i] = 2.0*(rand()%1000)/1000 - 1;
        }
    }
    return tmp;
}

int main(){
    ofstream fstep("target/step.txt",std::ios::out);
    ofstream ffit("target/fitness.txt",std::ios::out);
    srand(time(NULL));
    vector<Chromosome> chromosomes;
    for(int i = 0;i<20;i++){
        Chromosome tmp;
        chromosomes.push_back(tmp);
    }
    for(step = 0;step<100000;step++){
        vector<Chromosome> chromosomes2;


        for(int i = 0;i<20;i++){
            chromosomes[i].calcF();
        }
        std::sort (chromosomes.begin(), chromosomes.end(), myfunction);
        cout<<step<<" "<<chromosomes[0].fitness<<endl;
        fstep<<step<<endl;
        ffit<<chromosomes[0].fitness<<endl;
        printTable(chromosomes[0],step);
        if(chromosomes[0].fitness>31){
            return 0;
        }
        for(int i = 0;i<20;i++){
            int i1 = rand()%10;
            int i2;
            do{
                i2 = rand()%10;
            }while(i1==i2);
            Chromosome tmp = XOver(chromosomes[i1],chromosomes[i2]);
            chromosomes2.push_back(tmp);
        }
        chromosomes = chromosomes2;
    }
    fstep.flush();
    fstep.close();
    ffit.flush();
    ffit.close();
    ftableEtalonOut.flush();
    ftableEtalonOut.close();
    ftableIn.flush();
    ftableIn.close();
    ftableIsOk.flush();
    ftableIsOk.close();
    ftableOut.flush();
    ftableOut.close();

    return 0;
}
