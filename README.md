# Durability API

Gives modders better control over the durability bar. Has callbacks for visibility, fill value and color.

*A temporary solution until Fabric ships an official durability API*

## Usage

Add to your `build.gradle`: 

```
repositories {
  maven {
    url = 'https://maven.domi.re/'
  }
}

dependencies {
  modApi "re.domi:durability-api:1.0"
  include "re.domi:durability-api:1.0"
}
```

And to your `fabric.mod.json`'s `depends` block:

```
  "durabilityapi": ">=1.0",
```
