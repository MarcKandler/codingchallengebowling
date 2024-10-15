import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GameTest {

    private val game: Game = Game()

    private val perfectGame: Game = Game()

    @BeforeEach
    fun setup() {
        // Frame 1
        game.roll(1)
        game.roll(4)
        // Frame 2
        game.roll(4)
        game.roll(5)
        // Frame 3
        game.roll(6)
        game.roll(4)
        // Frame 4
        game.roll(5)
        game.roll(5)
        // Frame 5
        game.roll(10)
        // Frame 6
        game.roll(0)
        game.roll(1)
        // Frame 7
        game.roll(7)
        game.roll(3)
        // Frame 8
        game.roll(6)
        game.roll(4)
        // Frame 9
        game.roll(10)
        // Frame 10
        game.roll(2)
        game.roll(8)
        game.roll(6)
        game.printScore()
    }

    @Test
    fun `that total points are calculated correctly`() {

        assertEquals(5, game.frames[0].totalPoints)
        assertEquals(14, game.frames[1].totalPoints)
        assertEquals(29, game.frames[2].totalPoints)
        assertEquals(49, game.frames[3].totalPoints)
        assertEquals(60, game.frames[4].totalPoints)
        assertEquals(61, game.frames[5].totalPoints)
        assertEquals(77, game.frames[6].totalPoints)
        assertEquals(97, game.frames[7].totalPoints)
        assertEquals(117, game.frames[8].totalPoints)
        assertEquals(133, game.frames[9].totalPoints)
    }

    @Test
    fun `that no more rolls can be played after last frame`() {
        game.roll(2)

        assertEquals(133, game.frames[game.currentFrame].totalPoints)
    }

    @Test
    fun `that bonus points are calculated correctly`() {

        assertEquals(0, game.frames[0].bonus)
        assertEquals(0, game.frames[1].bonus)
        assertEquals(5, game.frames[2].bonus)
        assertEquals(10, game.frames[3].bonus)
        assertEquals(1, game.frames[4].bonus)
        assertEquals(0, game.frames[5].bonus)
        assertEquals(6, game.frames[6].bonus)
        assertEquals(10, game.frames[7].bonus)
        assertEquals(10, game.frames[8].bonus)
        assertEquals(0, game.frames[9].bonus)
    }

    @Test
    fun `that total points for perfect game are calculated correctly`() {
        perfectGame.roll(10)
        perfectGame.roll(10)
        perfectGame.roll(10)
        perfectGame.roll(10)
        perfectGame.roll(10)
        perfectGame.roll(10)
        perfectGame.roll(10)
        perfectGame.roll(10)
        perfectGame.roll(10)
        // Frame 10
        perfectGame.roll(10)
        perfectGame.roll(10)
        perfectGame.roll(10)

        assertEquals(30, perfectGame.frames[0].totalPoints)
        assertEquals(60, perfectGame.frames[1].totalPoints)
        assertEquals(90, perfectGame.frames[2].totalPoints)
        assertEquals(120, perfectGame.frames[3].totalPoints)
        assertEquals(150, perfectGame.frames[4].totalPoints)
        assertEquals(180, perfectGame.frames[5].totalPoints)
        assertEquals(210, perfectGame.frames[6].totalPoints)
        assertEquals(240, perfectGame.frames[7].totalPoints)
        assertEquals(270, perfectGame.frames[8].totalPoints)
        assertEquals(300, perfectGame.frames[9].totalPoints)
    }
}