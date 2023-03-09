# Evaluation: running commentary

## External DSL

_Describe each change from your ideal syntax to the syntax you implemented, and
describe_ why _you made the change._

I originally wanted it such that you could have a header which would represent a State, and then the rules after it. See:

State 1:
if [N: open] -> go N, change to State 1
if [N: open, S: blocked] -> go S, don't change State 

However, I originally found it difficult to implement this in my parser because I wasn't sure how to recognize each block as one state, and pass it to the Picobot API. Thus, I changed it to be:

From State 1, if [N: open] -> go N, change to State 1
From State 1, if [N: open, S: blocked] -> go S, don't change State 

Now that I'm a bit more familiar with how parsing works, though, I think I could potentially implement this; but alas, time. 

I also wanted to have key words "stay here" and "don't change State" but again, I was spending a lot of time on this already so I decided to just replace it with "go Nowhere" and "change to State X" where X is the original state. This simplified the parsing code because they have the same overall template. Again, I think I could definitely change this in the future if I wanted to, though.

**On a scale of 1–10 (where 10 is "a lot"), how much did you have to change your syntax?**
3. I explain why above.

**On a scale of 1–10 (where 10 is "very difficult"), how difficult was it to map your syntax to the provided API?**
6
The reason why I had to make these changes is because I spent a majority of my time trying to get my conditional to work (by conditional, I mean the surrounding state; [N: blocked, E: open]). The fact that in my DSL, N, E, W, S could occur in any order, and that some didn't have to occur at all, made it difficult to find a fitting regex that could parse this. Furthermore, the results would need to be fed into Surroundings as parameters.

What I ended up doing was creating a mutable map with four indices N, E, W, and S. I would call Surroundings with Surroundings(north = d("N"), south = d("S"), ...) where d is the map. These are all initially set to the value of Anything so that if a direction is not mentioned in the conditional list, then it is still in the map (for instance, if we had [N: blocked] then we would still need to have values representing E, W, and S). From there, the parser would break down the conditionals into smaller chunks ([N: blocked, S: open] would be broken down into N: blocked and S: open) and upon parsing that, the parser would return a tuple (direction, wallStatus) which would be fed into the map to update it (thus the need for a mutable map). 
