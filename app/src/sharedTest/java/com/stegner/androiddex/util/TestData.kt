package com.stegner.androiddex.util

import com.stegner.androiddex.data.pokemon.Pokemon
import com.stegner.androiddex.data.pokemon.PokemonNames
import com.stegner.androiddex.data.pokemon.PokemonStats

object MockData {

    fun seedData(): List<Pokemon> {

        val list = mutableListOf<Pokemon>()

        list.addAll(generateBugPokemon())
        list.addAll(generateDarkPokemon())
        list.addAll(generateDragonPokemon())
        list.addAll(generateElectricPokemon())
        list.addAll(generateFairyPokemon())
        list.addAll(generateFightPokemon())
        list.addAll(generateFirePokemon())
        list.addAll(generateFlyingPokemon())
        list.addAll(generateGhostPokemon())
        list.addAll(generateGrassPokemon())
        list.addAll(generateGroundPokemon())
        list.addAll(generateIcePokemon())
        list.addAll(generateNormalPokemon())
        list.addAll(generatePoisonPokemon())
        list.addAll(generatePsychicPokemon())
        list.addAll(generateRockPokemon())
        list.addAll(generateSteelPokemon())
        list.addAll(generateWaterPokemon())

        return list
    }

    private fun generateBugPokemon() : List<Pokemon>{
        val list = mutableListOf<Pokemon>()

        // Mono type
        val names1 = PokemonNames("Caterpie", "キャタピー", "绿毛虫", "Chenipan")
        val stats1 = PokemonStats(45,30,35,20,20,45)
        val types1 = listOf("Bug")
        val bug1 = Pokemon(10,1,names1, types1, stats1)
        list.add(bug1)

        // Dual type
        val names2 = PokemonNames("Weedle", "ビードル", "独角虫", "Aspicot")
        val stats2 = PokemonStats(40,35,30,20,20,50)
        val types2 = listOf("Bug", "Poison")
        val bug2 = Pokemon(13,1,names2, types2, stats2)
        list.add(bug2)

        return list
    }

    private fun generateDarkPokemon() : List<Pokemon>{
        val list = mutableListOf<Pokemon>()

        // Mono type
        val names1 = PokemonNames("Umbreon", "ブラッキー", "月亮伊布", "Noctali")
        val stats1 = PokemonStats(95,65,110,60,130,65)
        val types1 = listOf("Dark")
        val dark1 = Pokemon(197,2, names1, types1, stats1)
        list.add(dark1)

        // Dual type
        val names2 = PokemonNames("Sneasel", "ニューラ","狃拉", "Farfuret")
        val stats2 = PokemonStats(55,95,55,35,75,115)
        val types2 = listOf("Dark", "Ice")
        val dark2 = Pokemon(215,2, names2, types2, stats2)
        list.add(dark2)

        return list
    }

    private fun generateDragonPokemon() : List<Pokemon>{
        val list = mutableListOf<Pokemon>()

        // Mono type
        val names1 = PokemonNames("Dratini", "ミニリュウ", "迷你龙", "Minidraco")
        val stats1 = PokemonStats(41,64,45,50,50,50)
        val types1 = listOf("Dragon")
        val dragon1 = Pokemon(1,147, names1, types1, stats1)
        list.add(dragon1)

        // Dual type
        val names2 = PokemonNames("Dragonite", "カイリュー", "快龙", "Dracolosse")
        val stats2 = PokemonStats(91,134,95,100,100,80)
        val types2 = listOf("Dragon", "Flying")
        val dragon2 = Pokemon(149,1,names2, types2, stats2)
        list.add(dragon2)

        return list
    }

    private fun generateElectricPokemon() : List<Pokemon>{
        val list = mutableListOf<Pokemon>()

        // Mono type
        val names1 = PokemonNames("Jolteon", "サンダース", "雷伊布", "Voltali")
        val stats1 = PokemonStats(65,65,60,110,95,130)
        val types1 = listOf("Electric")
        val electric1 = Pokemon(135,1,names1, types1, stats1)
        list.add(electric1)

        // Dual type
        val names2 = PokemonNames("Emolga", "エモンガ", "电飞鼠", "Emolga")
        val stats2 = PokemonStats(55,75,60,75,60,103)
        val types2 = listOf("Electric", "Flying")
        val electric2 = Pokemon(587,5, names2, types2, stats2)
        list.add(electric2)

        return list
    }

    private fun generateFairyPokemon() : List<Pokemon>{
        val list = mutableListOf<Pokemon>()

        // Mono type
        val names1 = PokemonNames("Flabébé", "フラベベ", "花蓓蓓", "Flabébé")
        val stats1 = PokemonStats(44,38,39,61,79,42)
        val types1 = listOf("Fairy")
        val fairy1 = Pokemon(669,6, names1, types1, stats1)
        list.add(fairy1)

        // Dual type
        val names2 = PokemonNames("Carbink", "メレシー", "小碎钻", "Strassie")
        val stats2 = PokemonStats(50,50,150,50,150,50)
        val types2 = listOf("Rock", "Fairy")
        val fairy2 = Pokemon(703,6, names2, types2, stats2)
        list.add(fairy2)

        return list
    }

    private fun generateFightPokemon() : List<Pokemon>{
        val list = mutableListOf<Pokemon>()

        // Mono type
        val names1 = PokemonNames("Crabrawler", "マケンカニ", "好胜蟹", "Crabagarre")
        val stats1 = PokemonStats(78,82,57,42,47,63)
        val types1 = listOf("Fighting")
        val fighting1 = Pokemon(739,7, names1, types1, stats1)
        list.add(fighting1)

        // Dual type
        val names2 = PokemonNames("Crabominable", "ケケンカニ", "好胜毛蟹", "Crabominable")
        val stats2 = PokemonStats(97,132,77,62,67,43)
        val types2 = listOf("Fighting", "Ice")
        val fighting2 = Pokemon(740,7,names2, types2, stats2)
        list.add(fighting2)

        return list
    }

    private fun generateFirePokemon() : List<Pokemon>{
        val list = mutableListOf<Pokemon>()

        // Mono type
        val names1 = PokemonNames("Chimchar", "ヒコザル", "小火焰猴", "Ouisticram")
        val stats1 = PokemonStats(44,58,44,58,44,61)
        val types1 = listOf("Fire")
        val fire1 = Pokemon(390, 4, names1, types1, stats1)
        list.add(fire1)

        // Dual type
        val names2 = PokemonNames("Monferno", "モウカザル", "猛火猴", "Chimpenfeu")
        val stats2 = PokemonStats(64,78,52,78,52,81)
        val types2 = listOf("Fire", "Fighting")
        val fire2 = Pokemon(391,4,names2, types2, stats2)
        list.add(fire2)

        return list
    }

    private fun generateFlyingPokemon() : List<Pokemon>{
        val list = mutableListOf<Pokemon>()

        // Mono type
        val names1 = PokemonNames("Tornadus", "トルネロス", "龙卷云", "Boréas")
        val stats1 = PokemonStats(79,115,70,125,80,111)
        val types1 = listOf("Flying")
        val flying1 = Pokemon(641,5,names1, types1, stats1)
        list.add(flying1)

        // Dual type
        val names2 = PokemonNames("Vullaby", "バルチャイ","秃鹰丫头","Vostourno")
        val stats2 = PokemonStats(70,55,75,45,65,50)
        val types2 = listOf("Dark", "Flying")
        val flying2 = Pokemon(629,5, names2, types2, stats2)
        list.add(flying2)

        return list
    }

    private fun generateGhostPokemon() : List<Pokemon>{
        val list = mutableListOf<Pokemon>()

        // Mono type
        val names1 = PokemonNames("Misdreavus", "ムウマ","梦妖","Feuforêve")
        val stats1 = PokemonStats(60,60,60,85,85,85)
        val types1 = listOf("Ghost")
        val ghost1 = Pokemon(200,2,names1, types1, stats1)
        list.add(ghost1)

        // Dual type
        val names2 = PokemonNames("Shedinja","ヌケニン","脱壳忍者","Munja")
        val stats2 = PokemonStats(1,90,45,30,30,40)
        val types2 = listOf("Ghost", "Bug")
        val ghost2 = Pokemon(292,3, names2, types2, stats2)
        list.add(ghost2)

        return list
    }

    private fun generateGrassPokemon(): List<Pokemon>{
        val list = mutableListOf<Pokemon>()

        // Mono type
        val names1 = PokemonNames("Chikorita", "チコリータ", "菊草叶", "Germignon")
        val stats1 = PokemonStats(45,49,65,49,65,45)
        val types1 = listOf("Grass")
        val grass1 = Pokemon(152, 2,names1, types1, stats1)
        list.add(grass1)

        // Dual type
        val names2 = PokemonNames("Bulbasaur", "フシギダネ","妙蛙种子", "Bulbizarre")
        val stats2 = PokemonStats(45,49,49,65,65,45)
        val types2 = listOf("Grass", "Poison")
        val grass2 = Pokemon(1,1,names2, types2, stats2)
        list.add(grass2)

        return list
    }

    private fun generateGroundPokemon() : List<Pokemon>{
        val list = mutableListOf<Pokemon>()

        // Mono type
        val names1 = PokemonNames("Groudon", "グラードン", "固拉多", "Groudon")
        val stats1 = PokemonStats(100,150,140,100,90,90)
        val types1 = listOf("Ground")
        val ground1 = Pokemon(383,3,names1, types1, stats1)
        list.add(ground1)

        // Dual type
        val names2 = PokemonNames("Torterra", "ドダイトス", "土台龟", "Torterra")
        val stats2 = PokemonStats(95,109,105,75,87,56)
        val types2 = listOf("Grass", "Ground")
        val ground2 = Pokemon(389,4, names2, types2, stats2)
        list.add(ground2)

        return list
    }

    private fun generateIcePokemon() : List<Pokemon>{
        val list = mutableListOf<Pokemon>()

        // Mono type
        val names1 = PokemonNames("Snorunt","ユキワラシ","雪童子","Stalgamin")
        val stats1 = PokemonStats(50,50,50,50,50,50)
        val types1 = listOf("Ice")
        val ice1 = Pokemon(361,3,names1, types1, stats1)
        list.add(ice1)

        // Dual type
        val names2 = PokemonNames("Sealeo","トドグラー","海魔狮","Phogleur")
        val stats2 = PokemonStats(90,60,70,75,70,45)
        val types2 = listOf("Ice", "Water")
        val ice2 = Pokemon(364,3,names2, types2, stats2)
        list.add(ice2)

        return list
    }

    private fun generateNormalPokemon() : List<Pokemon>{
        val list = mutableListOf<Pokemon>()

        // Mono type
        val names1 = PokemonNames("Bidoof","ビッパ","大牙狸","Keunotor")
        val stats1 = PokemonStats(59,45,40,35,40,31)
        val types1 = listOf("Normal")
        val normal1 = Pokemon(399,4,names1, types1, stats1)
        list.add(normal1)

        // Dual type
        val names2 = PokemonNames("Bibarel","ビーダル","大尾狸","Castorno")
        val stats2 = PokemonStats(79,85,60,55,60,71)
        val types2 = listOf("Normal", "Water")
        val normal2 = Pokemon(400,4,names2, types2, stats2)
        list.add(normal2)

        return list
    }

    private fun generatePoisonPokemon() : List<Pokemon>{
        val list = mutableListOf<Pokemon>()

        // Mono type
        val names1 = PokemonNames("Trubbish","ヤブクロン","破破袋","Miamiasme")
        val stats1 = PokemonStats(50,50,62,40,62,65)
        val types1 = listOf("Poison")
        val poison1 = Pokemon(568,5, names1, types1, stats1)
        list.add(poison1)

        // Dual type
        val names2 = PokemonNames("Foongus","タマゲタケ","哎呀球菇","Trompignon")
        val stats2 = PokemonStats(69,55,45,55,55,15)
        val types2 = listOf("Poison", "Grass")
        val poison2 = Pokemon(598,5,names2, types2, stats2)
        list.add(poison2)

        return list
    }

    private fun generatePsychicPokemon() : List<Pokemon>{
        val list = mutableListOf<Pokemon>()

        // Mono type
        val names1 = PokemonNames("Cosmoem","コスモウム","科斯莫姆","Solgaleo")
        val stats1 = PokemonStats(43,29,131,29,131,37)
        val types1 = listOf("Psychic")
        val psychic1 = Pokemon(790,7, names1, types1, stats1)
        list.add(psychic1)

        // Dual type
        val names2 = PokemonNames("Malamar","カラマネロ","乌贼王","Sepiatroce")
        val stats2 = PokemonStats(86,92,88,68,75,73)
        val types2 = listOf("Psychic", "Dark")
        val psychic2 = Pokemon(687,6, names2, types2, stats2)
        list.add(psychic2)

        return list
    }

    private fun generateRockPokemon() : List<Pokemon>{
        val list = mutableListOf<Pokemon>()

        // Mono type
        val names1 = PokemonNames("Gigalith","ギガイアス","庞岩怪","Gigalithe")
        val stats1 = PokemonStats(85,135,130,60,80,25)
        val types1 = listOf("Rock")
        val rock1 = Pokemon(526,5, names1, types1, stats1)
        list.add(rock1)

        // Dual type
        val names2 = PokemonNames("Dwebble","イシズマイ","石居蟹","Crabicoque")
        val stats2 = PokemonStats(50,65,85,35,35,55)
        val types2 = listOf("Rock", "Bug")
        val rock2 = Pokemon(557,5,names2, types2, stats2)
        list.add(rock2)

        return list
    }

    private fun generateSteelPokemon() : List<Pokemon>{
        val list = mutableListOf<Pokemon>()

        // Mono type
        val names1 = PokemonNames("Registeel", "レジスチル", "雷吉斯奇鲁", "Registeel")
        val stats1 = PokemonStats(80,75,150,75,150,50)
        val types1 = listOf("Steel")
        val steel1 = Pokemon(379,3, names1, types1, stats1)
        list.add(steel1)

        // Dual type
        val names2 = PokemonNames("Magnemite", "コイル", "小磁怪", "Magnéti")
        val stats2 = PokemonStats(25,35,70,95,55,45)
        val types2 = listOf("Steel", "Electric")
        val steel2 = Pokemon(81,1,names2, types2, stats2)
        list.add(steel2)

        return list
    }

    private fun generateWaterPokemon() : List<Pokemon>{
        val list = mutableListOf<Pokemon>()

        // Mono type
        val names1 = PokemonNames("Samurott", "ダイケンキ", "大剑鬼", "Clamiral")
        val stats1 = PokemonStats(95,100,85,108,70,70)
        val types1 = listOf("Water")
        val water1 = Pokemon(503,5,names1, types1, stats1)
        list.add(water1)

        // Dual type
        val names2 = PokemonNames("Seismitoad", "ガマゲロゲ", "蟾蜍王", "Crapustule")
        val stats2 = PokemonStats(105,95,75,85,75,74)
        val types2 = listOf("Water", "Ground")
        val water2 = Pokemon(537,5, names2, types2, stats2)
        list.add(water2)

        return list
    }
}