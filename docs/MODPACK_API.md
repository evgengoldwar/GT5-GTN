# Modpack API

API для создания контента сборки на Minecraft 1.7.10 + EndlessIDs поверх этого форка GregTech.
Весь код лежит в `gregtech.api.modpack` (+ расширенная система материалов в `gregtech.common.MaterialSystem`).

Полный рабочий пример всех возможностей: `gregtech.api.modpack.example.ExampleModule`
(включается флагом JVM `-Dgregtech.modpack.example=true`).

## Содержание

1. [Как это устроено](#1-как-это-устроено)
2. [Материалы](#2-материалы)
3. [Простые блоки](#3-простые-блоки)
4. [Простые предметы](#4-простые-предметы)
5. [Сложные предметы (ПКМ, ЛКМ, тик)](#5-сложные-предметы)
6. [Карты рецептов и рецепты](#6-карты-рецептов-и-рецепты)
7. [Одноблочные машины](#7-одноблочные-машины)
8. [Хэтчи](#8-хэтчи)
9. [Мультиблоки](#9-мультиблоки)
10. [Провода и трубы](#10-провода-и-трубы)
11. [Каверы](#11-каверы)
12. [Текстуры: куда класть файлы](#12-текстуры)
13. [Таблица ID](#13-таблица-id)

---

## 1. Как это устроено

Контент описывается в **модулях**. Модуль — класс, реализующий `IModpackModule`. Каждый метод модуля
вызывается в нужный момент загрузки, поэтому не нужно думать о preInit/init/postInit.

```java
public class MyPack implements IModpackModule {

    @Override
    public String getName() {
        return "my_pack";
    }

    @Override public void registerMaterials()  { /* new MaterialBuilder(...)              */ }
    @Override public void registerRecipeMaps() { /* RecipeMapFactory.simple(...)          */ }
    @Override public void registerBlocks()     { /* BlockBuilder.create(...)              */ }
    @Override public void registerItems()      { /* MetaItem.create(...).add(...)         */ }
    @Override public void registerCovers()     { /* CoverBuilder.of(...)                  */ }
    @Override public void registerPipes()      { /* Pipes.cable(...) / fluidPipe / ...    */ }
    @Override public void registerMachines()   { /* MachineBuilder / MultiblockBuilder    */ }
    @Override public void registerRecipes()    { /* GTValues.RA.stdBuilder()... crafting  */ }
}
```

Регистрация модуля — в `gregtech.loaders.preload.ModpackModules#register()`:

```java
public static void register() {
    ModpackAPI.registerModule(new MyPack());
}
```

(Из другого мода — вызвать `ModpackAPI.registerModule(...)` в конструкторе своего `@Mod` класса,
он должен грузиться до GregTech.)

Порядок вызовов:

| Фаза FML | Метод модуля |
|---|---|
| preInit | `registerMaterials` → `registerRecipeMaps` → `registerBlocks` → `registerItems` → `registerCovers` → `registerPipes` → *(стандартные хэтчи)* → `registerMachines` |
| init | `init` |
| postInit | `registerRecipes` → `postInit` |

Если вызвать билдер не в той фазе, будет понятная ошибка `... must be registered during CONTENT phase`.

### Главное правило про ID

Всё, что сохраняется в мире, имеет постоянный номер: `meta` материала, `meta` подпредмета, `meta` машины,
`id` блока. **Никогда не меняйте их после выхода сборки** — иначе предметы в мирах превратятся в другие.
Раздавайте диапазоны заранее (см. [таблицу](#13-таблица-id)).

EndlessIDs снимает лимит на количество блоков и предметов, а метаданные блоков расширены до 16 бит —
поэтому, например, все корпуса материалов (до 32767 материалов) живут в одном блоке.

### Названия

Билдеры сами регистрируют английские названия (`.name("...")`). Русские — через `.nameRu("...")` или
файлом `assets/gregtech/lang/ru_RU.lang`. Утилита: `Lang.add(key, english)` / `Lang.add(key, Lang.RU, text)`.

---

## 2. Материалы

`MaterialBuilder` создаёт материал и **сразу генерирует** все выбранные предметы, блоки и жидкости.

```java
public static Material Iron;

Iron = new MaterialBuilder("Iron").meta(100)   // meta — постоянный ID материала
    .color(0xC8C8C8)                            // или .color(200, 200, 200)
    .addIngot()
    .addBolt()
    .addRod()
    .addFrame()                                  // блок-рамка (прозрачная, по ней можно лазить)
    .addBlock()
    .addMolten()                                 // расплавленное железо "molten.iron"
    .addPlasma()                                 // плазма "plasma.iron"
    .meltingPoint(1811)                          // температура расплава (K)
    .tooltip("Fe")                               // строка в тултипе всех предметов материала
    .build();
```

Что можно генерировать (`MaterialType`):

| Вид | Типы |
|---|---|
| Предметы | `DUST`, `SMALL_DUST`, `TINY_DUST`, `NUGGET`, `INGOT`, `HOT_INGOT`, `GEM`, `PLATE`, `DOUBLE_PLATE`, `DENSE_PLATE`, `FOIL`, `ROD`, `LONG_ROD`, `BOLT`, `SCREW`, `RING`, `SPRING`, `SMALL_SPRING`, `GEAR`, `SMALL_GEAR`, `ROTOR`, `FINE_WIRE`, `LENS` |
| Блоки | `BLOCK`, `CASING`, `FRAME` |
| Жидкости | `FLUID` (обычная), `MOLTEN` (расплав), `GAS` (газ), `PLASMA` (плазма) |

Удобные наборы:

```java
.addMetalParts()   // dust, small/tiny dust, nugget, ingot, plate, rod, bolt, screw, ring, gear, block, frame, molten
.addGemParts()     // dust, gem, plate, lens, rod, block
.add(MaterialType.FOIL, MaterialType.SPRING)   // любые типы списком
.remove(MaterialType.FRAME)                    // убрать лишнее из набора
```

Прочие настройки: `.localName("Stainless Steel")` (по умолчанию из имени `StainlessSteel`),
`.iconSet("shiny")` (свой набор текстур, см. §12), `.gasTemperature(...)`, `.plasmaTemperature(...)`.

Получение объектов:

```java
ItemStack bolts  = Iron.getStack(MaterialType.BOLT, 4);   // любой тип
ItemStack ingot  = Iron.getIngot(1);                      // короткие версии: getDust, getPlate, getRod, getBolt...
ItemStack frame  = Iron.getFrameStack(1);
Block casing     = Iron.getCasing();
FluidStack lava  = Iron.getMolten(144);                   // или getFluidStack(MaterialType.MOLTEN, 144)
FluidStack hot   = Iron.getPlasma(1000);
String oreName   = Iron.getOreDictName(MaterialType.BOLT); // "boltIron"
```

Все предметы и блоки автоматически регистрируются в Ore Dictionary (`ingotIron`, `boltIron`,
`stickIron`, `frameGtIron`, ...). Имена генерируются: "Iron Bolt", "Iron Frame Box", "Molten Iron", "Iron Plasma".

Встроенные материалы мода — `gregtech.common.MaterialSystem.Materials` (Steel = 1, Bronze = 2).

---

## 3. Простые блоки

Каждый блок — отдельный ID (с EndlessIDs их можно сколько угодно).

```java
public static SimpleBlock MARBLE;

MARBLE = BlockBuilder.create("marble")      // id: строчные буквы, без пробелов
    .name("Marble")
    .nameRu("Мрамор")
    .texture("blocks_custom/marble")         // assets/gregtech/textures/blocks/blocks_custom/marble.png (по умолчанию так и есть)
    .hardness(1.5F)
    .resistance(10F)
    .pickaxe(0)                              // .axe(1), .shovel(0), .tool("pickaxe", 2)
    .oreDict("stoneMarble")
    .tooltip("Красивый камень")
    .build();

ItemStack stack = MARBLE.getStack(4);
```

Другие возможности:

```java
.texture("top_tex", "bottom_tex", "side_tex")       // как бревно
.textures(down, up, north, south, west, east)       // по сторонам
.color(0xC03030)                                     // перекраска серой текстуры
.material(Material.glass).sound(Block.soundTypeGlass)
.transparent()          // как стекло (вырезка)
.translucent()          // полупрозрачный (как лёд)
.light(1.0F)            // светится как светокамень
.unbreakable()  .witherProof()  .beaconBase()
.drops(new ItemStack(Items.diamond))                 // дроп вместо самого блока
.onRightClick((world, x, y, z, player, side) -> { ...; return true; })
.onLeftClick((world, x, y, z, player) -> { ... })
.onWalk((world, x, y, z, entity) -> { ... })
```

Много блоков в цикле:

```java
String[] colors = { "red", "green", "blue" };
int[] rgb = { 0xC03030, 0x30A030, 0x3050C0 };
for (int i = 0; i < colors.length; i++) {
    BlockBuilder.create("concrete_" + colors[i])
        .name(colors[i] + " Concrete")
        .texture("blocks_custom/concrete")   // одна серая текстура
        .color(rgb[i])                        // разный цвет
        .build();
}
```

---

## 4. Простые предметы

`MetaItem` — один зарегистрированный предмет, внутри которого до 32767 подпредметов (по `meta`).
Удобно держать группы: «компоненты», «схемы», «инструменты».

```java
public static MetaItem COMPONENTS;
public static ItemEntry BASIC_CIRCUIT, LV_MOTOR, QUANTUM_CHIP;

COMPONENTS = MetaItem.create("components");

BASIC_CIRCUIT = COMPONENTS.add(1, "basic_circuit")  // meta 1 — постоянный ID
    .name("Basic Circuit")
    .nameRu("Простая схема")
    .tooltip("Tier: LV")                              // сколько угодно строк
    .oreDict("circuitBasic")
    .build();
// текстура по умолчанию: assets/gregtech/textures/items/items_custom/components/basic_circuit.png

LV_MOTOR = COMPONENTS.add(2, "lv_motor").name("LV Electric Motor").build();   // без тултипа

QUANTUM_CHIP = COMPONENTS.add(3, "quantum_chip")
    .name("Quantum Chip")
    .tooltip("Used in IV circuits")
    .rarity(EnumRarity.epic)   // цвет названия
    .glint()                   // блеск зачарования
    .build();
```

Использование:

```java
ItemStack two = BASIC_CIRCUIT.get(2);
boolean same  = BASIC_CIRCUIT.isSame(stack);
```

Ещё: `.texture("путь")`, `.color(0xRRGGBB)`, `.maxStackSize(16)`, `.burnTime(1600)` (топливо печки),
`.containerItem(stack)` (остаётся в верстаке, как ведро), `.hidden()` (не показывать в NEI/креативе).

---

## 5. Сложные предметы

Логика добавляется к тому же `ItemBuilder` — либо лямбдами, либо классом `ItemBehavior`.

### Лямбды

```java
TOOLS = MetaItem.create("tools");

HEALING_WAND = TOOLS.add(1, "healing_wand")
    .name("Healing Wand")
    .durability(64)                                   // прочность в NBT + полоска прочности
    .onRightClick((stack, world, player) -> {         // ПКМ в воздух
        if (!world.isRemote) {
            player.heal(4);
            ItemData.damage(stack, player, 1);        // -1 прочности, ломается сам
        }
        return stack;
    })
    .onLeftClickEntity((stack, player, target) -> {   // ЛКМ по мобу
        target.setFire(5);
        return false;                                  // false = обычный удар тоже проходит
    })
    .build();

SCANNER = TOOLS.add(2, "scanner")
    .name("Scanner")
    .onUseFirst((stack, player, world, x, y, z, side) -> {  // ПКМ по блоку ДО открытия GUI блока
        if (!world.isRemote) player.addChatComponentMessage(new ChatComponentText(world.getBlock(x, y, z).getLocalizedName()));
        return true;
    })
    .build();
```

Все хуки: `onRightClick`, `onUseOnBlock`, `onUseFirst`, `onLeftClickEntity`, `onRightClickEntity`,
`onSwing` (любой ЛКМ, в т.ч. по воздуху), `onTick` (каждый тик в инвентаре).

### Класс поведения

Для сложной логики — реализуйте `ItemBehavior` (все методы необязательные):

```java
public class MagnetBehavior implements ItemBehavior {

    private final double range;

    public MagnetBehavior(double range) {
        this.range = range;
    }

    @Override
    public ItemStack onRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            boolean on = ItemData.toggle(stack, "enabled");      // NBT-хелперы
            player.addChatComponentMessage(new ChatComponentText(on ? "On" : "Off"));
        }
        return stack;
    }

    @Override
    public void onTick(ItemStack stack, World world, Entity holder, int slot, boolean selected) {
        if (world.isRemote || !(holder instanceof EntityPlayer player) || !ItemData.getBoolean(stack, "enabled")) return;
        for (EntityItem item : (List<EntityItem>) world.getEntitiesWithinAABB(EntityItem.class, player.boundingBox.expand(range, range, range))) {
            item.setPosition(player.posX, player.posY, player.posZ);
        }
    }

    @Override
    public void addTooltip(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        tooltip.add(ItemData.getBoolean(stack, "enabled") ? "Enabled" : "Disabled");
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return ItemData.getBoolean(stack, "enabled");
    }
}

MAGNET = TOOLS.add(3, "magnet").name("Magnet").maxStackSize(1).behavior(new MagnetBehavior(8)).build();
```

Поведений у предмета может быть несколько (`.behavior(a).behavior(b)`).
Для «зажать ПКМ» (лук, еда, зарядка): `getUseDuration`, `getUseAction`, `onUsingTick`, `onStoppedUsing`, `onUseFinished`.

`ItemData` — хранение данных в NBT предмета: `getInt/setInt`, `getLong/setLong`, `getBoolean/setBoolean`,
`toggle`, `getString/setString`, `getDamage/setDamage/damage`.

---

## 6. Карты рецептов и рецепты

Карта рецептов = список рецептов одного типа машины + вкладка в NEI. Одна карта может использоваться
одноблоком и мультиблоком одновременно.

```java
public static RecipeMap<RecipeMapBackend> CRUSHER, MIXER, BIG_FURNACE;

// registerRecipeMaps():
CRUSHER = RecipeMapFactory.simple("modpack.recipe.crusher", "Crusher", 1, 2, 0, 0); // предм. вх/вых, жидк. вх/вых
MIXER   = RecipeMapFactory.simple("modpack.recipe.mixer",   "Mixer",   4, 1, 2, 1);

// полный контроль — стандартный билдер GregTech
BIG_FURNACE = RecipeMapFactory.builder("modpack.recipe.big_furnace", "Big Furnace")
    .maxIO(6, 6, 0, 0)
    .minInputs(1, 0)
    .progressBar(GTUITextures.PROGRESSBAR_ARROW)
    .build();
```

Рецепты (в `registerRecipes`):

```java
GTValues.RA.stdBuilder()
    .itemInputs(Iron.getIngot(1))
    .itemOutputs(Iron.getDust(1), Iron.getStack(MaterialType.TINY_DUST, 2))
    .outputChances(10000, 5000)       // 100% и 50%
    .fluidInputs(Water.getFluidStack(100))
    .fluidOutputs(...)
    .duration(200)                     // тики
    .eut(30)                           // EU/t
    .addTo(CRUSHER);
```

Программная схема в рецепте — просто добавьте её в `itemInputs` (у машины должен быть `.circuitSlot()`).

Крафты верстака/печки:

```java
CraftingRecipes.shaped(CRUSHER_LV.getStackForm(1),
    "PMP",
    "CHC",
    "PMP",
    'P', "plateIron", 'M', LV_MOTOR.get(), 'C', "circuitBasic", 'H', Hatches.get(HatchType.ENERGY, 1, 1));
CraftingRecipes.shapeless(Iron.getNugget(9), "ingotIron");
CraftingRecipes.smelting(Iron.getDust(1), Iron.getIngot(1), 0.1F);
```

---

## 7. Одноблочные машины

`MachineBuilder` делает GregTech-машину: корпус по тиру, приём энергии с проводов (со всех сторон,
кроме лицевой), предметные слоты, баки, работа по карте рецептов, оверклоки, GUI, автовыгрузка в
лицевую сторону (отвёртка — вкл/выкл предметы, Shift+отвёртка — жидкости), ключ — повернуть.

```java
// LV..EV дробилки: 4 машины, meta 1000..1003, названия "LV Crusher" ... "EV Crusher"
List<MTEBasicMachine> crushers = MachineBuilder.create("crusher")
    .name("%s Crusher")                 // %s -> LV, MV, HV...
    .recipeMap(CRUSHER)
    .items(1, 2)                        // 1 вход, 2 выхода
    .overlay("machines/crusher")        // текстуры лицевой стороны, см. §12
    .description("Crushes things into dust")
    .tiers(1, 4)                        // 1 = LV ... 14 = MAX
    .startMeta(1000)
    .register();

ItemStack lvCrusher = crushers.get(0).getStackForm(1);
```

Настройки:

| Метод | Что делает |
|---|---|
| `.items(in, out)` | количество предметных слотов (удобно до 6+6) |
| `.fluids(in, out)` / `.tankCapacity(l)` | баки |
| `.circuitSlot()` | слот программной схемы |
| `.amperage(a)` | сколько ампер можно тратить на рецепт |
| `.euStorage(eu)` | буфер энергии |
| `.perfectOverclock()` | идеальный оверклок (x4 скорость) |
| `.generator()` | генератор: рецепт **даёт** свой EU/t, энергия выходит из лицевой стороны |
| `.tier(t)` / `.tiers(min, max)` | тиры |

### Своя логика без своего класса — `MachineLogic`

```java
MachineBuilder.create("mixer").name("Basic Mixer").recipeMap(MIXER)
    .items(4, 1).fluids(2, 1).circuitSlot()
    .logic(new MachineLogic() {
        @Override
        public void modifyLogic(MTEBasicMachine machine, ProcessingLogic logic) {
            if (!machine.getGTE().getWorld().isDaytime()) logic.setSpeedBonus(0.5);  // ночью в 2 раза быстрее
        }

        @Override
        public CheckRecipeResult validateRecipe(MTEBasicMachine machine, GTRecipe recipe) {
            return machine.getGTE().getWorld().provider.dimensionId == -1
                ? CheckRecipeResultRegistry.SUCCESSFUL
                : SimpleCheckRecipeResult.ofFailure("only_nether");
        }

        @Override
        public void onRecipeFinish(MTEBasicMachine machine) { /* звук, частицы, ... */ }
    })
    .tier(1).startMeta(1010).register();
```

Хуки: `onTick`, `onWorkTick`, `modifyLogic`, `validateRecipe`, `onRecipeStart`, `onRecipeFinish`,
`onRightClick`, `addInfo`.

### Полностью своя логика — подкласс

```java
public class MTESolarCrusher extends MTEBasicMachine {

    public MTESolarCrusher(MachineConfig config) {   // единственный нужный конструктор
        super(config);
    }

    @Override
    protected CheckRecipeResult checkProcessing() {  // поиск/старт рецепта
        if (!getGTE().getWorld().isDaytime()) return SimpleCheckRecipeResult.ofFailure("night");
        return super.checkProcessing();
    }

    @Override
    protected void onWorkTick() {                     // каждый тик работы
        super.onWorkTick();
        if (getGTE().getBiome().biomeName.contains("Desert")) progress++;  // в пустыне x2
    }
}

MachineBuilder.create("solar_crusher").name("Solar Crusher").recipeMap(CRUSHER).items(1, 2)
    .factory(MTESolarCrusher::new)
    .tier(1).startMeta(1030).register();
```

Как в GregTech, машины под дождём могут загореться/взорваться (конфиг `sMachineRainExplosions`);
чтобы отключить для своей машины — переопределите `willExplodeInRain()` и верните `false`.

Что можно переопределять: `checkProcessing()` (машины без карты рецептов — насосы, буры, генераторы
на своём топливе: выставьте `maxProgress`, `eut`, `pendingItemOutputs`, `pendingFluidOutputs`),
`onWorkTick`, `outputProducts`, `onRecipeFinished`, `createProcessingLogic`, `getTexture`, `buildUI`
(свой GUI на ModularUI2), `onRightClick`, `saveNBTData/loadNBTData` (не забудьте `super`).

---

## 8. Хэтчи

Стандартные хэтчи создаются автоматически для тиров ULV..UV (0..8):

| `HatchType` | Что это |
|---|---|
| `INPUT_BUS` / `OUTPUT_BUS` | предметы, слотов (tier+1)², максимум 16 |
| `INPUT_HATCH` / `OUTPUT_HATCH` | жидкость, бак 8000·2^tier L |
| `ENERGY` / `DYNAMO` | приём / отдача энергии (2A) |

```java
ItemStack lvBus = Hatches.get(HatchType.INPUT_BUS, 1, 1);
```

Свой хэтч (например 4A энергетический):

```java
Hatches.register(5000, new MTEHatchEnergy(HatchType.ENERGY, 3, "hatch.energy4a.hv", "HV 4A Energy Hatch", 4));
```

Хэтч в собранном мультиблоке принимает текстуру корпуса мультиблока.

---

## 9. Мультиблоки

### Вариант 1: без своего класса — `MultiblockBuilder`

Форма пишется **послойно сверху вниз**, каждый слой — ряды **от лицевой стороны (где контроллер) назад**,
как будто смотришь сверху. Символы: `~` контроллер, `-` воздух, пробел — что угодно.

```java
MultiblockBuilder.create("big_furnace")
    .name("Big Furnace")
    .shape(new String[][] {
        { "CCC", "CCC", "CCC" },          // верхний слой
        { "H~H", "G-G", "CCC" },          // средний: контроллер спереди по центру, внутри воздух
        { "HHH", "HHH", "HHH" } })        // нижний слой
    .casing('C', Iron)                                    // корпус материала (MaterialType.CASING)
    .hatchOrCasing('H', Iron, HatchType.PROCESSING)       // корпус или любой хэтч обработки
    .element('G', MultiblockElements.block(Blocks.glass, 0))
    .minCasings(8)
    .requireHatches(HatchType.ENERGY, HatchType.INPUT_BUS, HatchType.OUTPUT_BUS)
    .recipeMap(BIG_FURNACE)
    .parallel(8)                    // 8 рецептов за раз
    .speed(2.0)                     // в 2 раза быстрее
    .euModifier(0.8)                // -20% энергии
    .overlay("machines/big_furnace")
    .description("Smelts 8 items at once")
    .meta(1100)
    .register();
```

Элементы структуры (`MultiblockElements`): `casing(block, meta)` / `casing(material)` (считается в
`getCasingCount`), `hatchOrCasing(...)`, `block(block, meta)`, `air()`, `any()`, `anyOf(a, b, ...)`,
а также любые элементы StructureLib (`StructureUtility.ofBlocksTiered` для катушек и т.п.).

Голографический проектор StructureLib строит такой мультиблок автоматически.
Энергия: напряжение = максимальное у энергохэтчей, ампер = количество энергохэтчей (как в GregTech).
`.generator()` — мультиблок-генератор: EU/t рецепта уходит в динамо-хэтчи.

Своя логика без класса:

```java
.logic(new MultiblockLogic() {
    @Override
    public boolean checkStructure(MTEConfigurableMultiblock m) {      // доп. условия структуры
        return m.getInputHatches().size() <= 2;
    }

    @Override
    public void modifyLogic(MTEConfigurableMultiblock m, ProcessingLogic logic) {   // бонус за лишние корпуса
        logic.setSpeedBonus(1.0 / Math.min(2.0, 1.0 + (m.getCasingCount() - 8) * 0.1));
    }

    @Override
    public void addInfo(MTEConfigurableMultiblock m, List<String> info) {
        info.add("Casings: " + m.getCasingCount());
    }
})
```

Также можно `.factory(MyMultiblock::new)` с подклассом `MTEConfigurableMultiblock`.

### Вариант 2: свой класс — `MTEMultiblockBase`

Для мультиблоков с несколькими частями структуры, переменной высотой, тирами катушек и т.д.:

```java
public class MTEBigFurnace extends MTEMultiblockBase {

    // static-поля создаются при загрузке класса (preInit) — одинаково на клиенте и сервере
    private static final int CASING = CasingTextures.register(Iron.getCasing(), Iron.getMeta());
    private static final IStructureDefinition<MTEMultiblockBase> STRUCTURE = StructureDefinition
        .<MTEMultiblockBase>builder()
        .addShape(MAIN_PIECE, StructureUtility.transpose(new String[][] {
            { "CCC", "CCC", "CCC" },
            { "H~H", "C-C", "CCC" },
            { "CCC", "CCC", "CCC" } }))
        .addElement('C', MultiblockElements.casing(Iron))
        .addElement('H', MultiblockElements.hatchOrCasing(Iron, HatchType.PROCESSING))
        .build();

    public MTEBigFurnace(String id) { super(id); }

    @Override public IMTE newMTE(IGTE gte) { return new MTEBigFurnace(name); }

    @Override public IStructureDefinition<MTEMultiblockBase> getStructureDefinition() { return STRUCTURE; }

    @Override
    protected boolean checkStructure() {
        // смещение контроллера: столбец 1, слой 1, ряд 0 (позиция '~')
        return checkPiece(MAIN_PIECE, 1, 1, 0) && getCasingCount() >= 12 && !getEnergyHatches().isEmpty();
    }

    @Override
    public void construct(ItemStack trigger, boolean hintsOnly) {
        buildPiece(MAIN_PIECE, trigger, hintsOnly, 1, 1, 0);
    }

    @Override protected int getCasingTextureId() { return CASING; }
    @Override public RecipeMap<?> getRecipeMap() { return BIG_FURNACE; }
    @Override protected int getMaxParallel() { return 4; }
    @Override public String[] getDescription() { return new String[] { "Smelts a lot" }; }
}

// registerMachines():
Prototypes.register(new MTEBigFurnace("big_furnace"), 600);
Lang.add("gt.blockmachines.big_furnace.name", "Big Furnace");
```

Переопределяемые методы: `checkProcessing` (своя логика без рецептов), `validateRecipe`, `modifyLogic`,
`onRecipeStart`, `onRecipeFinished`, `onWorkTick`, `addHatch` (свои типы деталей), `getOverlay`,
`buildUI`, `getInfoData`. Доступ к деталям: `getInputBuses()`, `getOutputBuses()`, `getInputHatches()`,
`getOutputHatches()`, `getEnergyHatches()`, `getDynamoHatches()`, `drainEnergy(eu)`, `addEnergyToDynamos(eu)`.

---

## 10. Провода и трубы

Каждый размер — отдельная машина-труба, поэтому указывается начальный `meta`: размеры занимают
подряд идущие номера.

```java
// провода 1x, 2x, 4x, 8x, 12x, 16x: meta 2000..2005
Pipes.cable(Copper, 2000)
    .voltage(GTValues.V[1])     // 32 V (LV)
    .amperage(1)                // ампер у 1x, у 4x будет 4
    .loss(2)                    // потери на блок
    .register();

// изолированные кабели (не бьют током, потери вдвое меньше): meta 2010..2015
Pipes.cable(Copper, 2010).voltage(GTValues.V[1]).amperage(1).loss(2).insulated().register();

// только нужные размеры
Pipes.cable(Gold, 2020).voltage(GTValues.V[3]).amperage(2).loss(4)
    .sizes(Pipes.CableSize.X1, Pipes.CableSize.X4).register();

// жидкостные трубы TINY..HUGE: meta 2030..2034
Pipes.fluidPipe(Steel, 2030)
    .capacity(20)               // L/t у TINY, у больших умножается
    .heatResistance(2500)       // K, горячее — труба горит
    .gasProof(true)
    .register();

// четверные / девятерные (несколько жидкостей сразу)
Pipes.fluidPipe(Steel, 2040).capacity(60)
    .sizes(Pipes.FluidPipeSize.QUADRUPLE, Pipes.FluidPipeSize.NONUPLE).register();

// предметные трубы SMALL..HUGE: meta 2050..2053
Pipes.itemPipe(Tin, 2050).register();
Pipes.itemPipe(Tin, 2060).restrictive().register();   // предметы обходят их, если есть другой путь

ItemStack cable4x = Pipes.getCable(Copper, Pipes.CableSize.X4, 1);
ItemStack pipe    = Pipes.getFluidPipe(Steel, Pipes.FluidPipeSize.NORMAL, 1);
```

Цвет берётся из материала, текстуры серые (см. §12). Свои текстуры: `.textures("side_tex", "end_tex")`.
Трубы и кабели можно красить баллончиком.

---

## 11. Каверы

Кавер — предмет, который ставится на сторону машины/трубы (Shift+ПКМ) и добавляет логику.

```java
// предметы (registerItems)
COVERS = MetaItem.create("covers");
SOLAR = COVERS.add(1, "solar_panel").name("Solar Panel Cover").build();
CONVEYOR = COVERS.add(2, "conveyor").name("Conveyor Cover").build();

// каверы (registerCovers)
CoverBuilder.of(SOLAR).texture("covers/solar_panel").behavior(CoverBehaviors.solarPanel(8)).register();
CoverBuilder.of(CONVEYOR).texture("covers/conveyor").behavior(CoverBehaviors.conveyor(1, 20)).register();

// декоративный кавер из пластины (отвёртка переключает, что пропускает сторона)
CoverBuilder.of(Iron.getPlate(1)).texture("covers/plate").decorative().register();
```

Готовые поведения (`CoverBehaviors`): `conveyor(стаков, тиков)`, `pump(литров, тиков)`,
`solarPanel(EU/t)`, `activityDetector()` (редстоун, пока машина работает), `machineController()`
(работа по редстоуну). У конвейера и помпы отвёртка переключает экспорт/импорт.

Свой кавер — реализуйте `CoverBehavior`:

```java
public class EnergyDetector implements CoverBehavior {

    @Override public int getTickRate() { return 10; }

    @Override
    public void onTick(SimpleCover cover, byte redstoneIn, long timer) {
        ICoverable tile = cover.getTile();
        long percent = tile.getEUCapacity() == 0 ? 0 : tile.getStoredEU() * 15 / tile.getEUCapacity();
        cover.setRedstoneOutput((int) percent);
    }

    @Override public boolean emitsRedstone(SimpleCover cover) { return true; }

    @Override
    public void onScrewdriver(SimpleCover cover, EntityPlayer player) {
        cover.cycleMode(player, "Normal", "Inverted");   // режим хранится в cover.getMode()
    }

    @Override public boolean letsItemsIn(SimpleCover cover, int slot) { return false; }  // блокирует вход предметов
}

CoverBuilder.of(MY_ITEM).texture("covers/energy_detector").behavior(new EnergyDetector())
    .onlyIf((side, stack, tile) -> tile instanceof IGTE)   // только на машины
    .register();
```

Данные кавера сохраняются в `cover.getData()` (NBT). Ещё в `CoverBuilder`: `.allowOnPrimitive()`,
`.blocksMachineGui()`, `.factory(ctx -> new MyCover(ctx, texture))` (свой подкласс `Cover` GregTech).

---

## 12. Текстуры

Все пути — относительно `src/main/resources/assets/gregtech/textures/`. В любом билдере можно указать
другой мод: `"mymod:path"`. Отсутствующая текстура не роняет игру — будет «missing texture».

| Что | Путь |
|---|---|
| Предметы материалов | `items/MaterialSystems/Items/<type>.png` (`bolt.png`, `rod.png`, ...) |
| Свой iconSet | `items/MaterialSystems/Items/<iconSet>/<type>.png` (если файла нет — берётся обычная) |
| Блоки материалов | `blocks/MaterialSystems/Blocks/<block\|casing\|frame>.png` |
| Жидкости | `blocks/MaterialSystems/Fluids/<fluid\|molten\|gas\|plasma>.png` (+ `.mcmeta` анимация) |
| Трубы | `blocks/MaterialSystems/Pipes/{wire, insulation, insulation_end, pipe_side, pipe_end, item_pipe_side, item_pipe_end}.png` |
| Простые блоки | `blocks/blocks_custom/<id>.png` (или `.texture(...)`) |
| Простые предметы | `items/items_custom/<metaitem>/<id>.png` (или `.texture(...)`) |
| Корпус машин | `blocks/machines/casing_{top,bottom,side}.png` (серые, красятся цветом тира) |
| Оверлей машины | `blocks/machines/<name>/overlay_{front,top,side}[_active].png` — все необязательные |
| Оверлей хэтчей | `blocks/machines/hatches/overlay_<type>.png` |
| Каверы | `blocks/covers/<name>.png` |

Текстуры материалов и труб должны быть **серыми** — цвет даёт материал. Добавлены простые
заглушки 16×16 для всех типов, их можно заменить своими. Свой корпус для тира:
`MachineTextures.setHull(tier, MachineTextures.Hull.of(top, bottom, side, 0xFFFFFF))`.

---

## 13. Таблица ID

| Что | Где | Диапазон |
|---|---|---|
| Материалы | meta материала | 0..32767 (Steel = 1, Bronze = 2) |
| Подпредметы `MetaItem` | meta внутри каждого MetaItem | 0..32766 |
| Машины, мультиблоки, свои хэтчи | блок машин GregTech (`MTEArrays.HATCH`) | 1..31999 |
| Стандартные хэтчи | там же, **зарезервировано** | 32000..32119 |
| Провода, трубы | блок кабелей (`MTEArrays.CABLE`) | 1..32765 (meta 1 занята тестовой трубой) |
| Простые блоки, MetaItem | строковый id | любые уникальные |

Пример раскладки: материалы 100–999, машины 1000–9999 (по 10 номеров на машину под тиры),
мультиблоки 10000–10999, трубы: по 10 номеров на материал.
