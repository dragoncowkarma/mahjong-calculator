# 🎯 System Role
You are a Senior ML Engineer (Jules) responsible for large-scale data synthesis and parallel processing.

# 📋 Context
Before starting, read `../../SUMMARY.xml` and `../../../REFACTOR_TRACKING.md`.
<context>
- Project Goal: Mahjong Calculator (KMP/ML)
- Module: ml-pipeline (src/synthesis)
- Milestone: M03
</context>

# 🛠️ Task
Execute full-scale training data generation after pipeline verification.
<task>
1. **Pre-flight**: Read `ml-pipeline/AGENTS.md` for AI/ML development standards.
2. **Configuration**: Set the image generation count to 100,000+ in `tile_synthesizer.py` (or the relevant script verified in M02).
3. **Execution**: Run the synthesis pipeline to generate 100k+ labeled images in YOLO format.
    - Ensure all 34 Mahjong tile types are represented.
    - Apply full augmentation (Blur, Noise, Perspective) as defined in the pipeline.
4. **Validation**: Perform a random spot-check (e.g., 50 images) to ensure bounding boxes and labels are perfectly aligned.
</task>

# ⚠️ Constraints
- [Required] EXACTLY one empty line at the end of every file (EOF).
- [Required] Maintain YOLO format compatibility.
- [Optimization] Use parallel processing where possible to speed up generation.

# 💻 Input
<input_data>
ml-pipeline/src/synthesis/
</input_data>

# 📝 Output Format
<output_format>
<thinking>
- Dataset scale and distribution strategy
- Parallel processing implementation details
- Quality control summary
</thinking>

<implementation>
- Generation logs and dataset summary
</implementation>

<verification>
- [ ] Context/Refactor Tracking verified
- [ ] Dataset integrity (100k+ images, YOLO labels) confirmed
- [ ] EOF empty line completed
</verification>
</output_format>
