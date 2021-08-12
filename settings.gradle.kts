rootProject.name = "airline"

include(":application")
include(":common:types")

include(":leasing:domain")
include(":leasing:usecase")
include(":leasing:persistence")
include(":leasing:web")

include(":flight:domain")
include(":flight:usecase")
include(":flight:persistence")
include(":flight:web")

include(":maintenance:domain")
include(":maintenance:usecase")
include(":maintenance:persistence")
include(":maintenance:web")
include(":integration:payment")