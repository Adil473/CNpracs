import random

n = int(input("Enter number of frames: "))
w = int(input("Enter window size: "))
pos=0 
ack=0

if(n==w):
    for i in range(0,w):
        print("Sending frame ",i)
    lost = random.randint(0,n)
    print("No acknowledgment recieved for frame ", lost)
    print("Retransmitting....")
    for i in range(0,w):
        print("Sending frame ",i)

while(pos<n):
    if(pos<w):
        print("Sending frame ",pos)
        pos+=1
    else:
        sent = random.randint(0, 1)  #0 for lost , 1 for sent
        if(sent==1):
            print("Acknowledgment for frame ", ack)
            print("Sending frame ", pos)
            ack+=1
            pos+=1
        else:
            print("No Acknnowldgment for frame ", ack)
            print("Retransmitting.....")
            pos = ack
            for i in range(0,w):
                print("Sending frame ", pos+i)
            pos = pos+w

