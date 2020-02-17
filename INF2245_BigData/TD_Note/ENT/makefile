CFLAGS=-O3 -mavx2 -std=gnu99
LDFLAGS=-lm
loops=50
prog= gray

test: $(prog)
	./$(prog) $(loops)

$(prog) : $(prog).c
	gcc $(CFLAGS) -o $(prog) $(prog).c $(LDFLAGS)
	
clean:
	rm -f $(prog)
	

	