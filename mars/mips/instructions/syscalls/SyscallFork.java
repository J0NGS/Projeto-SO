package mars.mips.instructions.syscalls;

import mars.mips.SO.ProcessManager.TabelaProcessos;
import mars.mips.hardware.RegisterFile;

public class SyscallFork {

    public SyscallFork() {
        TabelaProcessos.novo(RegisterFile.getValue(4));
    }
}
