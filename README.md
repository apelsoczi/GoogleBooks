# Google Books API
for: SIBS

---

### configuration:

define `apiKey` in your `local.properties`

```
apiKey=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
```

### notes:

submission deadline reached, opted to initially strictly follow the email instructions to build
with RxJava and binding. Project was modernized towards kotlin when the recruiter later confirmed
choices of libraries and binding vs jetpack compose was discretionary. Unfortunately, once the 
project was converted, many many many errors were found with compatibility between the Paging3
library and instructions from Google. Autoscroll and loading was not working by any means in this
project, and it was suspected that the compose migration was the reason. Paging3 library was
ultimately ditched when I saw it working in a dummy project I found online, when I copied their
code directly into my project, their source code did not work in this projects configuration, 
and likewise my code directly copied into the working project, did not work. 

The project includes a `paging3` branch with a logical implementation which is broken. Included
notes for workarounds which had to be performed to force unit tests to pass in a way which was
rather unconventional for view model testing, and after commit even those unit tests were broken.