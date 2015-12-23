#define FUNC_Q 2
#define ESC 27


#include <iostream>
#include <cmath>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <ctime>
#include <vector>
#include <list>
#include <chrono>
#include <unistd.h>
#include <termios.h>


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

int getch()
{
    int ch;
    struct termios oldt, newt;
    tcgetattr( STDIN_FILENO, &oldt );
    newt = oldt;
    newt.c_lflag &= ~( ICANON | ECHO );
    tcsetattr( STDIN_FILENO, TCSANOW, &newt );
    ch = getchar();
    tcsetattr( STDIN_FILENO, TCSANOW, &oldt );
    return ch;
}

mutex printlocker;
mutex calclocker;
mutex superlocker;
condition_variable workchecker;

volatile bool done = false, endWork = false;

long r1 = 0, r2 = 0, global = 0;
bool res1 = false, res2 = false;
bool notified[FUNC_Q];

void Determinator (long func (int), int parameter,  int i)
{
    {
        unique_lock <mutex> locker(printlocker);
        cout << "Function id = " << i << " incoming;" << endl;
    }

    std::this_thread::sleep_for(std::chrono::seconds(3*i));

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

void FuncF_Wrapper (int parameter, long &res) {
    {
        unique_lock <mutex> locker(printlocker);
        cout << "Function f(" << parameter << ") incoming;" << endl;
    }
    std::this_thread::sleep_for(std::chrono::seconds(3));
    {
        if (res1)
            return;
        unique_lock <mutex> locker (calclocker);
        {
            unique_lock <mutex> locker(printlocker);
            res = f(parameter);
            cout << "Func result = " << res << endl;
        }
        notified[0] = true;
        workchecker.notify_one();
    }

}

void FuncG_Wrapper (int parameter, long &res) {
    {
        unique_lock <mutex> locker(printlocker);
        cout << "Function f(" << parameter << ") incoming;" << endl;
    }
    std::this_thread::sleep_for(std::chrono::seconds(5));
    {
        if (res2)
            return;

        unique_lock <mutex> locker (calclocker);
        {
            unique_lock <mutex> locker(printlocker);
            res = g(parameter);
            cout << "Func result = " << res << endl;
        }
        notified[1] = true;
        workchecker.notify_one();
    }

}

bool ResultListener () {
    if (notified[0] && notified[1]) {
        done = true;
    }
    return done;
}

void CalculatingChecker (int a)
{
    {
        unique_lock<mutex> locker(printlocker);
        cout << "Checker running..." << endl;
    }

    {
        unique_lock<mutex> locker(calclocker);
        while (true) {

            if (!ResultListener()) {
                short p;
                {
                    unique_lock<mutex> uniqueLock(superlocker);
                    cout << "Enter your solution : ";
                    cin >> p;

                    if (p > 1) {
                        cout << "Im waiting" << endl;
                        std::this_thread::sleep_for(std::chrono::seconds(3));
                    }

                    else if (p == 1) {
                        std::this_thread::sleep_for(std::chrono::seconds(1));
                        continue;
                    }

                    else if (p <= 0) {
                        cout << "London good bye" << endl;
                        res1 = true;
                        res2 = true;
                        return;
                    }
                }
            }

            else {
                global = r1 + r2;
                cout << "Global solution : " << global << endl;
                break;
            }
            workchecker.notify_one();
        }
    }
    return;
}

int main()
{
    volatile const int a = 10;
    volatile const int b = 7;

    std::thread checker (CalculatingChecker, 1);
    std::thread funcF (FuncF_Wrapper, std::ref(a), std::ref(r1));
    std::thread funcG (FuncG_Wrapper, std::ref(b), std::ref(r2));

    funcF.join();
    funcG.join();
    cout << "Funcs joined" << endl;
    checker.join();
    cout << "Checker joined" << endl;

    cout << "good bye" << endl;

    return 0;
}
