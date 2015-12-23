#define FUNC_Q 2

#include <iostream>
#include <cmath>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <ctime>
#include <vector>
#include <list>
#include <chrono>

using namespace std;

long factorial (int value) {
    if (value == 0 || value == 1)
        return 1;
    return value * factorial(value-1);
}

long f (int param) {
    long res = factorial(param);
    return res;
}       // factorial

long g (int param) {
    long res = pow(param, param);
    return res;
}       // param ^ param;

mutex printlocker;
mutex calclocker;
condition_variable workchecker;

volatile bool done, endWork = false;
bool notified[FUNC_Q];

void Determinator (long (*func)(int), int parameter,  int i)
{
    {
        unique_lock <mutex> locker(printlocker);
        cout << "Function id = " << i << " incoming;" << endl;
    }

    std::this_thread::sleep_for(chrono::seconds(3*i));

    {
        unique_lock <mutex> locker (calclocker);
        {
            unique_lock <mutex> locker(printlocker);
            cout << "Func " << i << " ressult = " << func(parameter) << endl;
        }
        notified[i+1] = true;
        workchecker.notify_one();
    }
}

bool ResultListener () {
    if (notified[0] && notified[1]) {
        done = true;
        return done;
    }
}

void CalculatingChecker (int a)
{
    {
        unique_lock<mutex> locker(printlocker);
        cout << "Checker running..." << endl;
    }

    unique_lock <mutex> locker (calclocker);
    while (!ResultListener())
        workchecker.wait(locker);

}


int main()
{
    int a = 5;
    int b = 3;

    thread checker(CalculatingChecker);

    list <thread> thrds;
    thrds.emplace_back(thread(Determinator, f, a, 1));
    thrds.emplace_back(thread(Determinator, g, b, 2));

    for (auto &t : thrds) {
        t.join();
    }
    endWork = true;
    checker.join();

    cout << "good bye" << endl;

    return 0;
}








