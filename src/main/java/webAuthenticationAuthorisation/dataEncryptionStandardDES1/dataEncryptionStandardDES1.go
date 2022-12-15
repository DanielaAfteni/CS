package dataencryptionstandarddes1

var (
	permutationChoice1 = [56]byte{
		57, 49, 41, 33, 25, 17, 9,
		1, 58, 50, 42, 34, 26, 18,
		10, 2, 59, 51, 43, 35, 27,
		19, 11, 3, 60, 52, 44, 36,
		63, 55, 47, 39, 31, 23, 15,
		7, 62, 54, 46, 38, 30, 22,
		14, 6, 61, 53, 45, 37, 29,
		21, 13, 5, 28, 20, 12, 4,
	}

	permutationChoice2 = [48]byte{
		14, 17, 11, 24, 1, 5,
		3, 28, 15, 6, 21, 10,
		23, 19, 12, 4, 26, 8,
		16, 7, 27, 20, 13, 2,
		41, 52, 31, 37, 47, 55,
		30, 40, 51, 45, 33, 48,
		44, 49, 39, 56, 34, 53,
		46, 42, 50, 36, 29, 32,
	}

	initialPermutationTable = [64]byte{
		58, 50, 42, 34, 26, 18, 10, 2,
		60, 52, 44, 36, 28, 20, 12, 4,
		62, 54, 46, 38, 30, 22, 14, 6,
		64, 56, 48, 40, 32, 24, 16, 8,
		57, 49, 41, 33, 25, 17, 9, 1,
		59, 51, 43, 35, 27, 19, 11, 3,
		61, 53, 45, 37, 29, 21, 13, 5,
		63, 55, 47, 39, 31, 23, 15, 7,
	}

	p_table_test = [32]byte{
		16, 7, 20, 21,
		29, 12, 28, 17,
		1, 15, 23, 26,
		5, 18, 31, 10,
		2, 8, 24, 14,
		32, 27, 3, 9,
		19, 13, 30, 6,
		22, 11, 4, 25,
	}

	expansionTable = [48]byte{
		32, 1, 2, 3, 4, 5,
		4, 5, 6, 7, 8, 9,
		8, 9, 10, 11, 12, 13,
		12, 13, 14, 15, 16, 17,
		16, 17, 18, 19, 20, 21,
		20, 21, 22, 23, 24, 25,
		24, 25, 26, 27, 28, 29,
		28, 29, 30, 31, 32, 1,
	}

	finalPermutationTable = [64]byte{
		40, 8, 48, 16, 56, 24, 64, 32,
		39, 7, 47, 15, 55, 23, 63, 31,
		38, 6, 46, 14, 54, 22, 62, 30,
		37, 5, 45, 13, 53, 21, 61, 29,
		36, 4, 44, 12, 52, 20, 60, 28,
		35, 3, 43, 11, 51, 19, 59, 27,
		34, 2, 42, 10, 50, 18, 58, 26,
		33, 1, 41, 9, 49, 17, 57, 25,
	}

	sBox = [8][4][16]byte{
		// S-box 1
		{
			{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
			{0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
			{4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
			{15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13},
		},
		// S-box 2
		{
			{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
			{3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
			{0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
			{13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9},
		},
		// S-box 3
		{
			{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
			{13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
			{13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
			{1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12},
		},
		// S-box 4
		{
			{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
			{13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
			{10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
			{3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14},
		},
		// S-box 5
		{
			{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
			{14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
			{4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
			{11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3},
		},
		// S-box 6
		{
			{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
			{10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
			{9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
			{4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13},
		},
		// S-box 7
		{
			{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
			{13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
			{1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
			{6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12},
		},
		// S-box 8
		{
			{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
			{1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
			{7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
			{2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11},
		},
	}

	keyShiftRotations = [16]uint8{1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1}
)

const (
	BLOCKSIZE = 64
)

func DesEncrypt(plaintext uint64, key uint64) uint64 {
	return desEncryption(plaintext, key, false)
}

func DesDecrypt(plaintext uint64, key uint64) uint64 {
	return desEncryption(plaintext, key, true)
}

func desEncryption(plaintext uint64, key uint64, encrypt bool) uint64 {
	subkeys := generateSubkeys(key, permutationChoice1[:], permutationChoice2[:], keyShiftRotations[:])
	if !encrypt {
		for i, j := 0, len(subkeys)-1; i < j; i, j = i+1, j-1 {
			subkeys[i], subkeys[j] = subkeys[j], subkeys[i]
		}
	}

	ip := permuteBlockUniversal(plaintext, initialPermutationTable[:], 64)
	left, right := splitNr(ip, 64)

	leftIterations := make([]uint32, 16)
	rightIterations := make([]uint32, 16)

	leftIterations[0], rightIterations[0] = des_Iteration(left, right, subkeys[0], expansionTable[:], p_table_test[:])
	for i := 1; i < 16; i++ {
		leftIterations[i], rightIterations[i] = des_Iteration(leftIterations[i-1], rightIterations[i-1], subkeys[i], expansionTable[:], p_table_test[:])
	}

	r16l16 := mergeNr(uint64(rightIterations[15]), uint64(leftIterations[15]), 64)

	finalMessage := permuteBlockUniversal(r16l16, finalPermutationTable[:], 64)
	return finalMessage
}

func splitNr(message uint64, size int) (uint32, uint32) {
	left := uint32(message >> (size / 2))
	right := uint32(message)
	return left, right
}

func mergeNr(left, right uint64, size int) uint64 {
	return left<<(size/2) | right
}

func generateSubkeys(key uint64, pc1, pc2, ksR []uint8) (subkeys []uint64) {
	subkeys = make([]uint64, 16)
	k0 := permuteBlockUniversal(key, pc1[:], 64)
	c, d := splitNr(k0<<4, 64)
	d = d >> 4
	for i := 0; i < 16; i++ {
		c = (c<<(4+ksR[i]))>>4 | (c >> (28 - ksR[i]))
		d = (d<<(4+ksR[i]))>>4 | (d >> (28 - ksR[i]))
		tempJoin := uint64(c)<<28 | uint64(d)
		subkeys[i] = permuteBlockUniversal(tempJoin, pc2[:], 56)
	}

	return subkeys
}

func permuteBlockUniversal(src uint64, permutation []uint8, srcSize int) (block uint64) {
	for pos, n := range permutation {
		bit := (src >> (uint8(srcSize) - n)) & 1
		block |= bit
		if pos < len(permutation)-1 {
			block = block << 1
		}
	}
	return
}

func des_Iteration(l0, r0 uint32, subkey uint64, exp_table, p_table []byte) (l_iteration uint32, r_iteration uint32) {
	l_iteration = r0
	r_iteration = l0 ^ fFunction(r0, subkey, exp_table, p_table)
	return
}

func fFunction(r uint32, k uint64, expP, p_table []byte) (fblock uint32) {
	er := permuteBlockUniversal(uint64(r), expP[:], 32)
	eRK := er ^ k
	sp := sBoxPerm(eRK, sBox)
	fblock = uint32(permuteBlockUniversal(uint64(sp), p_table[:], 32))
	return
}

func sBoxPerm(eRK uint64, sBoxes [8][4][16]byte) (sb uint32) {
	eRK_Groups := make([]byte, 8)
	sb = 0

	for i := 0; i < 8; i++ {
		eRK_Groups[i] = byte((eRK << (16 + i*6)) >> 58)

		b00 := eRK_Groups[i]
		bx := (b00 << 3) >> 4
		by := ((b00 & 0b00100000) >> 4) | (b00 & 1)
		b1 := sBoxes[i][by][bx]

		sb = sb | uint32(b1)<<((8-i-1)*4)
	}

	return
}

func appendBytes(plaintext *[]uint64) {
	tail := 0
	l := len(*plaintext)
	if l%BLOCKSIZE != 0 {
		tail = (BLOCKSIZE - l) % BLOCKSIZE
	} else {
		return
	}
	tailspace := make([]uint64, tail)
	*plaintext = append(*plaintext, tailspace...)
}

func splitStringBlocks(plaintext string) (messageA []uint64) {
	for len(plaintext)%8 != 0 {
		plaintext += "0"
	}

	messageA = make([]uint64, len(plaintext)/8)

	for i := 0; i < len(plaintext)/8; i++ {
		var block uint64
		for j := 0; j < 8; j++ {
			block |= uint64([]byte(plaintext)[i*8+j]) << ((7 - j) * 8)
		}
		messageA[i] = block
	}
	return
}

func mergeStringBlocks(messageA []uint64) (messageS string) {
	messageS = ""
	for i := 0; i < len(messageA); i++ {
		var stringBlock string
		for j := 0; j < 8; j++ {
			stringBlock += string(byte(messageA[i] >> ((7 - j) * 8)))
		}
		messageS += stringBlock
	}
	return
}
