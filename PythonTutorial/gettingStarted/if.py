#!/usr/bin/python
# filename: if.py

num=23
runing=True
while runing:
	guess = int(raw_input('enter an integer'))

	if guess == num:
		print 'you gussed it'
		break
		runing = False
		print runing

	elif guess < num:
		print 'your guess is less than that'
	elif guess > num:
		print 'larger'
	else:
		print 'your guess is larger than that'
else:
	print 'the while loop is over'

print 'done'
print runing

for i in range(1,5):
	print i
else:
	print 'end'


while True:
	s=raw_input('enter somthing: ')
	if len(s) < 3:
		continue
	if len(s) == 3:
		print s
		break
	print 'input is of sufficient length'

	


