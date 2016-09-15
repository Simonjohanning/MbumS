simstruct_or = SetInitialValue(simstruct_or, 'BMP2', 0)
simstruct_or = SetInitialValue(simstruct_or, 'TGFb1', 1)
[t,y] = OdefySimulation(simstruct_or, 0, 0);
plot(t,y, {".", ".", 'd', '*', 'p'}, "markersize", 2);
legend(simstruct_or.model.species);
xlabel("time");
ylabel("expression");
title("Runx2->Dlx5 inhibitory, TGFb1 only, disjunction");
axis([0, 10, -0.1, 1.1]);