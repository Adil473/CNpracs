#include <bits/stdc++.h>
using namespace std;

// int calcr(string str, int len){
//     int nor;
//     nor= log2(len)+1;
//     return nor;
// }

string convertbin(int n,int nor){
   string bin="";
   for(int i=nor-1;i>=0;i--){
    int k = n >> i;
        if (k & 1)
            bin=bin+"1";
        else
            bin=bin+"0";
   }
    return bin;
}

int calcno(int len){
    bool is_found=false;
    int r=0;
    while(is_found!=true){
        if((pow(2,r)>len+r+1)||(pow(2,r)==len+r+1)){
            is_found=true;
            return r;
        }
        else{
            r++;
        }
    }
}

string hcgenerator(string db, int nor){
    string hcg="";int x=0; int d=0;
    reverse(db.begin(),db.end());
    for(int i=1;i<=db.length()+nor;i++){
        if(i==pow(2,x)){
            hcg=hcg+"x";
            x++;
            // cout<<hcg<<"\n";
        }
        else{
            hcg= hcg+db[d];
            d++;
            // cout<<hcg<<"\n";
        }
    }
    for(int i=0;i<nor;i++){
        int count=0;
        for(int x=0;x<pow(2,nor);x++){
            string bin;
            //dec to bin
            bin=convertbin(x,nor);
            //cout<<x<<"-"<<bin<<"\n";
            reverse(bin.begin(),bin.end());
            //cout<<"reversedbin-"<<bin<<"\n";
            if(bin[i]=='1'){
                // cout<<x<<"-"<<bin<<" "<<str[x-1]<<"\n";
                if(hcg[x-1]=='1'){
                    count++;
                }
            }
        }
        if((count%2)!=0){
            hcg[pow(2,i)-1]='1';
        }
        else if((count%2)==0){
            hcg[pow(2,i)-1]='0';
        }
    }
    reverse(hcg.begin(),hcg.end());
    return hcg;
}


int onebitedc(string str, const int nor){
    string finalr="";
    for(int i=0;i<nor;i++){
        int count=0;
        for(int x=0;x<pow(2,nor);x++){
            string bin;
            //dec to bin
            bin=convertbin(x,nor);
            //cout<<x<<"-"<<bin<<"\n";
            reverse(bin.begin(),bin.end());
            //cout<<"reversedbin-"<<bin<<"\n";
            if(bin[i]=='1'){
                // cout<<x<<"-"<<bin<<" "<<str[x-1]<<"\n";
                if(str[x-1]=='1'){
                    count++;
                }
            }
        }
        if((count%2)!=0){
            finalr=finalr + "1";
        }
        else if((count%2)==0){
            finalr=finalr+"0";
        }

    }
    reverse(finalr.begin(),finalr.end());
    // cout<<"finalr"<<finalr;
    if(stoi(finalr)==0){
        cout<<"No error is detected\n";
        return 0;
    }
    else{
       int errorbit;
        //bin to dec
        //cout<<"reached";
        errorbit=stoi(finalr,0,2);
        return errorbit;
    }

}

int twobited(string str,int c){
    int p; int count=0;
    for(int i=0;i<str.length();i++){
        if(str[i]=='1'){
            count++;
        }
    }
    if((count%2)!=0){
        p=1;
    }
    else{
        p=0;
    }
    if((p==0)&&(c==0)){
        cout<<"No error eccured\n";
        return 0;
    }
    else if((p==1)&&(c!=0)){
        cout<<"single error eccured\n";
        return 0;
    }
    else if((p==0)&&(c!=0)){
        cout<<"double error eccured\n";
        return 0;
    }
}

int main(){
    string str; int r; int len;string db;string hc; int nor;string str1;
    cout<<"Enter data bits\n";
    cin>>db;
    nor=calcno(db.length());
    cout<<"the no. of redundant bits are "<<nor<<"\n";
    hc=hcgenerator(db,nor);
    cout<<"Hamming code is "<<hc<<"\n";
    cout<<"1 bit error detection and correction- Enter string\n";
    cin>> str;
    len=str.length();
    // r=calcr(str,len);
    reverse(str.begin(),str.end());
    int errorbit= onebitedc(str,nor);
    if(errorbit!=0){
    cout<<"Error is detected at "<<errorbit<<"th bit"<<"\n"; 
        if(str[errorbit-1]=='0'){
        str[errorbit-1]='1';
       }
        else if(str[errorbit-1]=='1'){
        str[errorbit-1]='0';
       }
       reverse(str.begin(),str.end());
       cout<<"The corrected string is "+ str+"\n";
    }
    cout<<"Two bit error detection- Enter string \n";
    cin>>str1;
    reverse(str.begin(),str.end());
    int c= onebitedc(str1,nor);
    twobited(str1,c);
}